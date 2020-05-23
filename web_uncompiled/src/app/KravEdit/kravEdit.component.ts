import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserData} from '../models/UserData.model';
import {projectFunctionality} from '../models/projectFunctionality.model';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material';
import {MatDialog} from '@angular/material/dialog';
import {statusPageData} from '../models/statusPageData.model';
import {TranslateService} from '@ngx-translate/core';
import {ProjectRequirment} from '../models/ProjectRequirment.model';
import {Observable} from "rxjs";
import {Links} from "../models/links.model";
import { ConcurrencyResolver } from '../Modals/ConcurrencyResolver/ConcurrencyResolver.component';
import {DeleteRequirmentDialog} from "../Modals/RemoveReq/RemoveReq.component";
import { ShareMenu } from '../Modals/ShareMenu/shareMenu.component';

@Component({
  selector: 'app-root',
  templateUrl: './kravEdit.component.html',
  styleUrls: [
    './kravEdit.component.css',
    '../common.css'
  ]
})
// tslint:disable-next-line:class-name
export class kravEditComponent implements OnInit {

  public title = 'Grouse';

  sideBarOpen: boolean;
  currentReq: projectFunctionality;
  userData: UserData;
  mainData: projectFunctionality[];
  newReqPriority: string;
  selectedTab: number;
  maxID: number;
  statusPage: boolean;
  statpageData: statusPageData;
  loading: boolean;
  treeControl: NestedTreeControl<Requirment>;
  dataSource: MatTreeNestedDataSource<Requirment>;
  statusbarData: projectFunctionality[];

  private nav: Requirment[];
  private dialog: MatDialog;
  private projectLink: string;
  private http: HttpClient;
  private router: Router;

  constructor(http: HttpClient, router: Router, dialog: MatDialog, public translate: TranslateService) {
    this.loading = true;
    this.http = http;
    this.router = router;
    this.projectLink = '';
    this.sideBarOpen = false;
    this.dialog = dialog;
    this.statusbarData = [];
    this.statusPage = false;
    this.statpageData = new statusPageData();
    translate.addLangs(['Bokmaal', 'English', 'Nynorsk']);
    translate.setDefaultLang('Bokmaal');
  }

  /**
   * enterUserEdit
   * Opens the user edit page
   */
  enterUserEdit() {
    this.userData.nav = 'userEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/userEdit']);
  }

  /**
   * enterAdminPage
   * Opens the administration page, only available to admins
   */
  enterAdminPage() {
    if (this.userData._links["project-list-all"] === undefined){
      // The button should not be visible unless you have admin privileges
      console.warn('This user does not have admin privileges, you shouldt have been able to do this please contact an administrator!');
    } else {
      this.userData.nav = 'Admin';
      localStorage.setItem('UserData', JSON.stringify(this.userData));
      this.router.navigate(['/Admin'])
    }
  }

  ngOnInit() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    this.translate.setDefaultLang(this.userData.defaultLang);
    this.projectLink = this.userData.currentProject._links.function.href;
    this.fetchMainData();
    this.treeControl = new NestedTreeControl<Requirment>(
      node => node.children
    );
    this.dataSource = new MatTreeNestedDataSource<Requirment>();
    this.dataSource.data = TREE_DATA;
  }

  /**
   * fetchMainData
   *
   * A Monster method that calls all required links to fetch the entire project form the server,
   * also generates the tree that builds up the sidenav
   */
  fetchMainData() {
    this.loading = true;
    this.http.get(this.projectLink, {
      headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        }
      )
    }).subscribe(result => {
      const newlyLoaded = (this.mainData === undefined);
      // @ts-ignore
      this.mainData = result._embedded.projectFunctionalities;
      // Calls all children, but makes sure that it is finnished before it proceeds
      let calls = 0;
      this.maxID = 0;
      const NavData: Requirment[] = [];
      for (const prime of this.mainData) {
        const NavReqP = new Requirment();
        NavReqP.id = prime.projectFunctionalityId;
        NavReqP.name = prime.title;
        NavReqP.children = [];
        if (prime._links.function !== undefined) {
          calls++;
          this.http.get(prime._links.function.href, {
            headers: new HttpHeaders({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret,
              }
            ),

            // tslint:disable-next-line:no-shadowed-variable
          }).subscribe(result => {
            calls--;
            // @ts-ignore
            prime.referenceChildProjectFunctionality = result._embedded.projectFunctionalities;

            // Does it all again if there are more levels of children
            for (const secondary of prime.referenceChildProjectFunctionality) {
              secondary.parent = prime;
              const NavReqS = new Requirment();
              NavReqS.id = secondary.projectFunctionalityId;
              NavReqS.name = secondary.title;
              NavReqS.children = [];
              if (secondary._links.function !== undefined) {
                calls++;
                this.http.get(secondary._links.function.href, {
                  headers: new HttpHeaders({
                      Authorization: 'Bearer ' + this.userData.oauthClientSecret
                    }
                  )
                  // tslint:disable-next-line:no-shadowed-variable
                }).subscribe(result => {
                  // @ts-ignore
                  secondary.referenceChildProjectFunctionality = result._embedded.projectFunctionalities;
                  // Does it all again again for the next generation
                  for (const tertiary of secondary.referenceChildProjectFunctionality) {
                    tertiary.parent = secondary;
                    const NavReqT = new Requirment();
                    NavReqT.id = tertiary.projectFunctionalityId;
                    NavReqT.name = tertiary.title;
                    NavReqS.children.push(NavReqT);
                    if (tertiary._links.requirement !== undefined) {
                      calls++;
                      this.http.get(tertiary._links.requirement.href, {
                        headers: new HttpHeaders({
                            Authorization: 'Bearer ' + this.userData.oauthClientSecret
                          }
                        )
                        // tslint:disable-next-line:no-shadowed-variable
                      }).subscribe(result => {
                        // @ts-ignore
                        tertiary.referenceProjectRequirement = this.fixRequirmentsText(result._embedded.projectRequirements);

                        // Fetches Etags for theese requirments
                        this.getEtag(tertiary.referenceProjectRequirement).subscribe(etags => {
                          tertiary.referenceProjectRequirement = etags;
                          calls--;

                          //If this was the last call that returned data collection is finished
                          if (calls === 0) {
                            this.crunchGatheredData(newlyLoaded);
                          }
                        });
                        // tslint:disable-next-line:no-shadowed-variable
                      }, error => {
                        console.error(error);
                      });
                    }
                    if (this.maxID < tertiary.projectFunctionalityId) {
                      this.maxID = tertiary.projectFunctionalityId;
                    }
                  }

                  // Fetches Etags for theese requirments
                  this.getEtag(secondary.referenceChildProjectFunctionality).subscribe(etags => {
                    secondary.referenceChildProjectFunctionality = etags;
                    calls--;

                    //If this was the last call that returned data collection is finished
                    if (calls === 0) {
                      this.crunchGatheredData(newlyLoaded);
                    }
                  });
                  // tslint:disable-next-line:no-shadowed-variable
                }, error => {
                  console.error(error);
                });
              } else if (secondary._links.requirement !== undefined) {
                calls++;
                this.http.get(secondary._links.requirement.href, {
                  headers: new HttpHeaders({
                      Authorization: 'Bearer ' + this.userData.oauthClientSecret
                    }
                  )
                  // tslint:disable-next-line:no-shadowed-variable
                }).subscribe(result => {
                  // @ts-ignore
                  secondary.referenceProjectRequirement = this.fixRequirmentsText(result._embedded.projectRequirements);

                  // Fetches Etags for theese requirments
                  this.getEtag(secondary.referenceProjectRequirement).subscribe(etags => {
                    secondary.referenceProjectRequirement = etags;
                    calls--;

                    //If this was the last call that returned data collection is finished
                    if (calls === 0) {
                      this.crunchGatheredData(newlyLoaded);
                    }
                  });
                  // tslint:disable-next-line:no-shadowed-variable
                }, error => {
                  console.error(error);
                });
              }
              if (this.maxID < secondary.projectFunctionalityId) {
                this.maxID = secondary.projectFunctionalityId;
              }
              NavReqP.children.push(NavReqS);
            }

            // Fetches Etags for theese functionalities
            this.getEtag(prime.referenceChildProjectFunctionality).subscribe(etags => {
              prime.referenceChildProjectFunctionality = etags;
              calls--;

              //If this was the last call that returned data collection is finished
              if (calls === 0) {
                this.crunchGatheredData(newlyLoaded);
              }
            });
            // tslint:disable-next-line:no-shadowed-variable
          }, error => {
            console.error(error);
          });
        } else if (prime._links.requirement !== undefined) {
          calls++;
          this.http.get(prime._links.requirement.href, {
            headers: new HttpHeaders({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
              }
            )
            // tslint:disable-next-line:no-shadowed-variable
          }).subscribe(result => {
            // @ts-ignore
            prime.referenceProjectRequirement = this.fixRequirmentsText(result._embedded.projectRequirements);

            // Fetches Etags for theese requirments
            this.getEtag(prime.referenceProjectRequirement).subscribe(etags => {
              prime.referenceProjectRequirement = etags;
              calls--;

              //If this was the last call that returned data collection is finished
              if (calls === 0) {
                this.crunchGatheredData(newlyLoaded);
              }
            });
            // tslint:disable-next-line:no-shadowed-variable
          }, error => {
            console.error(error);
          });
        }
        if (this.maxID < prime.projectFunctionalityId) {
          this.maxID = prime.projectFunctionalityId;
        }
        NavData.push(NavReqP);
      }
      this.dataSource.data = NavData;
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.error(error);
    });
  }

  /**
   * getEtag
   * Fetches Etags for all requirments or projectFunctionalities in given array, this is asyncrounous so it returns a subscribable observable
   * @param data The array of requirments to fetch etags for
   */
  getEtag(data: ProjectRequirment[] | projectFunctionality[]): Observable<any>{
    let calls = 0;
    return new Observable<ProjectRequirment[] | projectFunctionality[]>(observer => {
      for (let element of data) {
        calls++;
        this.http.get(element._links.self.href, {
          headers: new HttpHeaders({
            Authorization: 'Bearer ' + this.userData.oauthClientSecret
          }),
          observe: "response"
        }).subscribe(result => {
          calls--;
          element.etag = result.headers.get("etag");
          if (calls === 0) {
            observer.next(data);
            observer.complete();
          }
        }, error => {
          console.error(error);
        })
      }
    });
  }

  /**
   * downloadDocument
   * Downloads the document from the server to the users local machine
   */
  downloadDocument() {
    this.statpageData.generatingDocument = true;
    this.http.get(this.userData.currentProject._links.document.href, {
      headers: new HttpHeaders({
          Authorization: 'Bearer' + this.userData.oauthClientSecret,
          Accept: this.statpageData.selectedFormat.type
        }
      ),
      responseType: 'blob'
    }).subscribe(result => {
      this.statpageData.generatingDocument = false;
      const url = window.URL.createObjectURL(result);
      const a = document.createElement('a');
      a.setAttribute('style', 'display:none;');
      a.href = url;
      document.body.appendChild(a);
      a.download = this.userData.currentProject.projectName + "." + this.statpageData.selectedFormat.extension;
      a.click();
      return url;
    });
  }

  /**
   * fixRequirmentText
   * Fixes the formating of requirments for the gui, this will most likely be removed at a later date
   *
   * @param requirments
   * An array of requirments to fix
   */
  fixRequirmentsText(requirments: ProjectRequirment[]): ProjectRequirment[] {
    const ret: ProjectRequirment[] = [];
    for (const req of requirments) {
      let text = '';
      for (const line of req.requirementText.split('\n')) {
        let newline = ' ';
        let passed = false; // When the space is "driven-past" cahnges to true
        for (const character of line.split('')) {
          if (character !== ' ') {
            passed = true;
          }
          if (passed) {
            newline += character;
          }
        }
        text += newline;
      }
      if (text.split('')[0] === ' ') {
        text = text.substring(1);
      }
      const newreq: ProjectRequirment = req;
      newreq.requirementText = text;
      ret.push(newreq);
    }
    return ret;
  }

  /**
   * crunchGatheredData
   *
   * Is called by fetch mian data the data needs to be treated differently depending on if it was the first time it was loaded
   * @param newlyLoaded
   * A booleann that tells the method if this was the first time that the data was fetched or not.
   */
  crunchGatheredData(newlyLoaded: boolean) {
    this.loading = false;
    this.sortMainData();
    // this.convertLegacyLinks();
    if (newlyLoaded) {
      const first = this.mainData[0];
      if (first.referenceProjectRequirement === undefined) {
        this.currentReq = first.referenceChildProjectFunctionality[0];
      } else {
        this.currentReq = first;
      }
      this.selectedTab = 0;
    } else {
      this.changeFunctionality(this.currentReq.projectFunctionalityId);
    }
    this.statusBarInfo();

    this.getSupportedFormats();
  }

  /**
   * sortMainData
   * Sorts the main data in ascending order
   */
  sortMainData() {
    this.mainData.sort((a, b) => {
      return parseInt(a.functionalityNumber) - parseInt(b.functionalityNumber)
    });
    for (let primary of this.mainData) {
      if(primary.referenceChildProjectFunctionality !== undefined && primary.referenceChildProjectFunctionality.length > 0){
        for (let secondary of primary.referenceChildProjectFunctionality) {
          if (secondary.referenceChildProjectFunctionality !== undefined && secondary.referenceChildProjectFunctionality.length > 0) {
            secondary.referenceChildProjectFunctionality.sort((a, b) => {
              return parseInt(a.functionalityNumber) - parseInt(b.functionalityNumber)
            });
          }
        }
        primary.referenceChildProjectFunctionality.sort((a, b) => {
          return parseInt(a.functionalityNumber) - parseInt(b.functionalityNumber)
        });
      }
    }
  }

  /**
   * goToMainMenu
   *
   * This method sends the user to the main menu when called
   */
  goToMainMenu() {
    this.userData.nav = 'Menu';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/Menu']);
  }

  /**
   * logout
   *
   * This method both sends a call to the server to invalidate the current auth-token and sends the user to the login page
   * As this method is duplicated it will proabobly be moved later
   */
  logout() {
    localStorage.clear();
    this.http.get(this.userData._links['logout OAuth2'].href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.error(error);
    });
    this.router.navigate(['/']);
    location.reload();
  }

  /**
   * updateFunctionalityEtag
   * Updates the etag that is stored in memory to the one that is sent in as a parameter
   *
   * This is only used to update the etag when you change the processed state, if i find a better way to solve
   * this i will implement it, so this might disappear later
   *
   * @param functionality The updated functionality to update the memory to
   */
  updateFunctionalityEtag(functionality: projectFunctionality) {
    const inMemory = this.findFunctionality(functionality.projectFunctionalityId);

    if( inMemory !== null) {
      if (inMemory.parent === undefined) {
        const index = this.mainData.indexOf(
          this.mainData.find(func => func.projectFunctionalityId === functionality.projectFunctionalityId
          ));
        if (index !== -1) {
          this.mainData[index].etag = functionality.etag;
        }
      } else if (inMemory.parent !== undefined) {

        if (inMemory.parent.parent !== undefined) {
          const primeIndex = this.mainData.indexOf(
            this.mainData.find(func => func.projectFunctionalityId === inMemory.parent.parent.projectFunctionalityId
            ));
          const secIndex = this.mainData[primeIndex].referenceChildProjectFunctionality.indexOf(
            this.mainData[primeIndex].referenceChildProjectFunctionality.find(
              func => func.projectFunctionalityId === inMemory.parent.projectFunctionalityId
            ));
          const trdIndex = this.mainData[primeIndex].referenceChildProjectFunctionality[secIndex].referenceChildProjectFunctionality.indexOf(
            this.mainData[primeIndex].referenceChildProjectFunctionality[secIndex].referenceChildProjectFunctionality.find(
              func => func.projectFunctionalityId === inMemory.projectFunctionalityId
            )
          );

          this.mainData[primeIndex].referenceChildProjectFunctionality[secIndex].referenceChildProjectFunctionality[trdIndex].etag = functionality.etag;
        } else {

          const primeIndex = this.mainData.indexOf(
            this.mainData.find(func => func.projectFunctionalityId === inMemory.parent.projectFunctionalityId
            ));
          const secIndex = this.mainData[primeIndex].referenceChildProjectFunctionality.indexOf(
            this.mainData[primeIndex].referenceChildProjectFunctionality.find(
              func => func.projectFunctionalityId === inMemory.projectFunctionalityId
            )
          );

          this.mainData[primeIndex].referenceChildProjectFunctionality[secIndex].etag = functionality.etag;
        }
      }
    }
  }

  /**
   * changeFunctionality
   *
   * Takes the ID of a projectFunctionality and jumps to that functionality, used to select between different parts of the project
   *
   * @param id
   * Wich id to change to
   */
  changeFunctionality(id: number) {
    if (id === 0) { // Loads the statuspage
      this.statusPage = !this.statusPage;
      this.statPageLoad();
    } else {
      this.currentReq = this.findFunctionality(id);
      this.newReqPriority = 'O';
      this.selectedTab = 0;
      this.statusBarInfo();
      this.statusPage = false;
    }
  }

  /**
   * findFunctionality
   *
   * Finds the requirment inn main data with the specified id
   * @param id
   * Wich id to look for
   */
  findFunctionality(id: number): projectFunctionality {
    // Primary level
    let pfound = this.mainData.find(functionality => functionality.projectFunctionalityId === id);
    if(pfound === undefined) {
      for (let primary of this.mainData) {
        if (primary.referenceChildProjectFunctionality.length > 0) {
          // Secondary level
          for (const secondary of primary.referenceChildProjectFunctionality) {
            if (secondary.referenceChildProjectFunctionality !== undefined) {
              // Tertiary level
              for (const tertiary of secondary.referenceChildProjectFunctionality) {
                if (tertiary.projectFunctionalityId === id) {
                  return tertiary;
                }
              }
            } else if (secondary.projectFunctionalityId === id) {
              return secondary;
            }
          }
        } else if (primary.projectFunctionalityId === id) {
          return primary;
        }
      }
      return null;
    } else {
      return pfound;
    }
  }

  /**
   * updateRequirementText
   *
   * handles a change in requirement text.
   */
  updateRequirementText(index: number) {
    const req = this.currentReq.referenceProjectRequirement[index];

    // @ts-ignore
    req.requirementText = document.getElementById('field-' + index).value;

    const patch = [{ op: "replace", path: "/requirementText", value: req.requirementText}];

    this.sendPatch(patch, req._links.self.href, req.etag).subscribe(result => {
      this.currentReq.referenceProjectRequirement[index] = result;
    });
  }

  /**
   * updateFunctionalityProcessed
   *
   * Toggles the proccesed boolean flag og a projectfuntionality
   *
   * @param functionality
   * The functionalioty to update.
   */
  updateFunctionalityProcessed(functionality: projectFunctionality) {
    functionality.processed = !functionality.processed;

    const patch: IPatchObject[] = [{ op: "replace", path: "/processed", value:
      functionality.processed}];

    this.sendPatch(patch, functionality._links.self.href, functionality.etag).subscribe(result => {
      this.updateFunctionalityEtag(result);

      // Cheks to se if all children of current parent has been processed unless this is a prime
      if (functionality.parent !== undefined && functionality.parent !== null) {
        let check = true;
        for (const child of functionality.parent.referenceChildProjectFunctionality) {
          if (!child.processed) {
            check = false;
          }
        }
        if (check !== functionality.parent.processed) {
          this.updateFunctionalityProcessed(functionality.parent);
        }
      }
    });
  }

  /**
   * updateRequirementPriority
   *
   * handles a change in requirement priority.
   *
   * @param index
   * Wich index of the requirment array within the projectFunctionality that should be updated.
   *
   * @param priority
   * The priority to assign the requirment
   */
  updateRequirementPriority(index: number, priority: string) {
    const patch = [{ op: "replace", path: "/priority", value:
      priority}];

    let req = this.currentReq.referenceProjectRequirement[index];

    this.sendPatch(patch, req._links.self.href, req.etag).subscribe(result => {
      this.currentReq.referenceProjectRequirement[index] = result;
    });
  }

  /**
   * sendPatch
   *
   * Sends patches to the server, used by all update functions that deal with requirements returns an observable with the updated data so that it can be shifted into memory
   *
   * @param patch
   * Inputted patch to send
   *
   * @param url
   * Inputted url to send patchstring to
   *
   * @param etag
   * Etag to accompany the patch
   */
  sendPatch(patch: IPatchObject[], url: string, etag?: string): Observable<any> {
    return new Observable<ProjectRequirment>(observer => {
      this.http.patch(url, patch, {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret,
          ETAG: etag
        }),
        observe: "response"
      }).subscribe(result => {
        // @ts-ignore
        let returned: any = result.body;
        returned.etag = result.headers.get("etag");
        observer.next(returned);
        observer.complete();
      }, error => {
        if (error.error !== undefined) {
          // Concurrency error detected
          if (error.error.status === 409) {
            // Catches concurrency errors, ignores concurrency errors for processed, as this is not necessary
            if (patch[0].path !== "/processed") {
              this.resolveConcurrencyError(url, patch).subscribe(result => {
                observer.next(result);
                observer.complete();
              });
            } else {
              // "ignoring" processed
              this.http.get(url, {
                headers: new HttpHeaders({
                  Authorization: 'Bearer ' + this.userData.oauthClientSecret
                }),
                observe: "response"
              }).subscribe(result => {
                this.http.patch(url, patch, {
                  headers: new HttpHeaders({
                    Authorization: 'Bearer ' + this.userData.oauthClientSecret,
                    ETAG: result.headers.get('etag')
                  }),
                  observe: "response"
                }).subscribe(result => {
                  let returned: any = result.body;
                  returned.etag = result.headers.get("etag");
                  observer.next(returned);
                  observer.complete();
                }, error2 => {
                  console.error(error2);
                });
              }, error1 => {
                console.error(error1)
              });
            }
          } else {
            console.error(error);
          }
        }
      });
    });
  }

  /**
   * addRequirment
   *
   * Adds a requirment to the project on the currently selected projectFuncionality
   */
  addRequirment() {
    const textfield = document.getElementById('NyttKrav');

    this.http.post(
      this.currentReq._links.requirement.href,
      {
        // @ts-ignore
        requirementText: textfield.value
      }, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // @ts-ignore
      textfield.value = null;
      this.fetchMainData();
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.error(error);
    });
  }

  /**
   * removeRequirment
   *
   * Removes a requirment from the project
   *
   * @param index
   * Wich index of the requirment array withtin the currently selected projectFunctionality to delete
   */
  removeRequirment(index: number) {
    const dialogref = this.dialog.open(DeleteRequirmentDialog, {
      width: '300px'
    });

    dialogref.afterClosed().subscribe(result => {
      if (result) {
        this.http.delete(
          this.currentReq.referenceProjectRequirement[index]._links.self.href,
          {
            headers: new HttpHeaders({
              Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
          }).subscribe(
          // tslint:disable-next-line:no-shadowed-variable
            result => {
              this.fetchMainData();
              // tslint:disable-next-line:no-shadowed-variable
            }, error => console.error(error));
      }
    });
  }

  /**
   * newReqPriorityChange
   *
   * Updates the newReqPriority datafield wich is used to determine what new requirments prioriteis are.
   *
   * @param priority
   * What to update the priority to
   */
  newReqPriorityChange(priority: string) {
    this.newReqPriority = priority;
  }

  /**
   * nextReq
   *
   * Switches to the next requirment
   */
  nextReq() {
    const switchto = this.findNextReq(this.currentReq.projectFunctionalityId);
    if (switchto !== null) {
      this.currentReq = switchto;
      this.newReqPriority = 'O';
      this.selectedTab = 0;
    } else {
      this.statusPage = !this.statusPage;
      this.statPageLoad();
    }
    this.statusBarInfo();
  }

  /**
   * findNextReq
   * Finds the next requirment based on the given ID
   *
   * @param id
   * ID to search for next of
   */
  findNextReq(id: number): projectFunctionality {
    let switchto: projectFunctionality = null;
    id++;
    while (switchto === null) {
      // This if-statment stops the function from going into a endless-loop
      if (id > this.maxID) {
        return null;
      }
      switchto = this.findFunctionality(id);
      if (switchto !== null && switchto.referenceProjectRequirement === undefined) {
        switchto = null;
      }
      id++;
    }
    return switchto;
  }

  /**
   * previousReq
   *
   * Switches to the previous rquirment
   */
  previousReq() {
    const switchto = this.findPreviousReq(this.currentReq.projectFunctionalityId);
    if (switchto !== null) {
      this.currentReq = switchto;
      this.newReqPriority = 'O';
      this.selectedTab = 0;
    }
    this.statusBarInfo();
  }

  /**
   * findPreviousReq
   * Finds the previous requirment based on given ID
   *
   * @param id
   * ID to find next requirment of
   */
  findPreviousReq(id: number): projectFunctionality {
    let switchto: projectFunctionality = null;
    id--;
    while (switchto === null) {
      // This if-statment stops the function from going into a endless-loop
      if (id < this.mainData[0].projectFunctionalityId) {
        return null;
      }
      switchto = this.findFunctionality(id);
      if (switchto !== null && switchto.referenceProjectRequirement === undefined) {
        switchto = null;
      }
      id--;
    }
    return switchto;
  }

  /**
   * statusBarInfo
   *
   * Provides an array for the statusbar with the previous 5 and next 5 requirments
   * This directly updates the datafield, wich will then force the UI to load the elements
   */
  statusBarInfo() {
    const ret: projectFunctionality[] = [this.currentReq];
    for (let i = 0; i < 5; i++) {
      const add = this.findPreviousReq(ret[0].projectFunctionalityId);
      if (add === null) {
        break;
      } else {
        ret.unshift(add);
      }
    }
    while (ret.length < 11) {
      const add = this.findNextReq(ret[ret.length - 1].projectFunctionalityId);
      if (add !== null) {
        ret.push(add);
      } else if (ret.length > 9) {
        ret.unshift(this.findPreviousReq(ret[0].projectFunctionalityId));
        // tslint:disable-next-line:no-shadowed-variable
        const add = {
          functionalityNumber: 'Ferdig',
          projectFunctionalityId: 0,
        };
        // @ts-ignore
        ret.push(add);
      } else {
        ret.unshift(this.findPreviousReq(ret[0].projectFunctionalityId));
      }
    }
    this.statusbarData = ret;
  }

  /**
   * statPageLoad
   *
   * Loads and crunches all required data for the statpage
   */
  statPageLoad() {
    this.sideBarOpen = false;
    this.statpageData.loaded = false;
    this.statpageData.unfinished = [];
    this.getSupportedFormats().subscribe(result => {this.statpageData.sportedFormats = result;});
    let add: projectFunctionality = this.currentReq;
    while (add !== null) {
      if (!add.processed) {
        this.statpageData.unfinished.unshift(add);
      }
      add = this.findPreviousReq(add.projectFunctionalityId);
      // tslint:disable-next-line:max-line-length
      if ( add !== null && this.statpageData.unfinished.length > 0 && this.statpageData.unfinished[0].projectFunctionalityId === add.projectFunctionalityId) {
        add = null;
      }
    }

    if (this.statpageData.unfinished.length === 0) {
      this.statpageData.finished = true;
    } else {
      this.statpageData.finished = false;
    }

    if (!this.statpageData.finished) {
      add = this.mainData[0];
      let lastID = 0;
      let numberOfReqs = 0;
      let finished = 0;
      while (add !== null && add.projectFunctionalityId !== lastID) {
        if (add.processed) {
          finished++;
        }
        lastID = add.projectFunctionalityId;
        add = this.findNextReq(lastID);
        numberOfReqs++;
      }
      this.statpageData.progress = Math.round((finished / numberOfReqs) * 100);
    } else {
      this.statpageData.progress = 100;
    }
    this.statpageData.loaded = true;
  }

  /**
   * getSupportedFormats
   * Fetches available formats from the server
   */
  getSupportedFormats(): Observable<IFormat[]> {
    return new Observable<IFormat[]>(observer => {
      this.http.get<ISupportedFormatResponse>(this.userData.currentProject._links["supported-formats"].href, {
        headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      let formats: IFormat[] = [];
        if (result.supportedFormats["application/vnd.openxmlformats-officedocument.wordprocessingml.document"] !== undefined && result.supportedFormats["application/vnd.openxmlformats-officedocument.wordprocessingml.document"] !== "") {
          formats.push({
            type: "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            extension: result.supportedFormats["application/vnd.openxmlformats-officedocument.wordprocessingml.document"]
          });
        }
        if (result.supportedFormats["application/vnd.oasis.opendocument.text"] !== undefined && result.supportedFormats["application/vnd.oasis.opendocument.text"] !== "") {
          formats.push({
            type: "application/vnd.oasis.opendocument.text",
            extension: result.supportedFormats["application/vnd.oasis.opendocument.text"]
          });
        }
        if (result.supportedFormats["text/html"] !== undefined && result.supportedFormats["text/html"] !== "") {
          formats.push({
            type: "text/html",
            extension: result.supportedFormats["text/html"]
          });
        }
        if (result.supportedFormats["application/pdf"] !== undefined && result.supportedFormats["application/pdf"] !== "") {
          formats.push({
            type: "application/pdf",
            extension: result.supportedFormats["application/pdf"]
          });
        }
        if (result.supportedFormats["application/rtf"] !== undefined && result.supportedFormats["application/rtf"] !== "") {
          formats.push({
            type: "application/rtf",
            extension: result.supportedFormats["application/rtf"]
          });
        }
        if (result.supportedFormats["text/asciidoc"] !== undefined && result.supportedFormats["text/asciidoc"] !== "") {
          formats.push({
            type: "text/asciidoc",
            extension: result.supportedFormats["text/asciidoc"]
          });
        }
        if (result.supportedFormats["text/markdown"] !== undefined && result.supportedFormats["text/markdown"] !== "") {
          formats.push({
            type: "text/markdown",
            extension: result.supportedFormats["text/markdown"]
          });
        }
        observer.next(formats);
        observer.complete();
      }, error => {
        console.error(error);
        observer.next(null);
        observer.complete();
      });
    });
  }

  /**
   * openShareMenu
   * Opens a dialog where the user can add or remove other users from having acces to the current project*
   */
  openShareMenu() {
    this.dialog.open(ShareMenu, {
      data: this.userData,
      width: '500px'
    });
  }

  /**
   * resolveConcurrencyError
   * Opens a dialog that allows the user to resolve a concurrency error
   * @param url The url of the requirement that is being updated
   * @param patch The original patch that was attempted
   */
  resolveConcurrencyError(url: string, patch: IPatchObject[]): Observable<ProjectRequirment> {
    const data: IConcurrencyDetails = {
      url: url,
      patch: patch,
      token: this.userData.oauthClientSecret
    }

    const dialogRef = this.dialog.open(ConcurrencyResolver, {
      data: data,
      width: '90%',
      minWidth: '500px',
      maxWidth: '1000px',
      height: '90%',
      minHeight: '750px',
      maxHeight: '100px'
    });

    return new Observable<ProjectRequirment>(observer => {
      dialogRef.afterClosed().subscribe(result => {
        observer.next(result);
        observer.complete();
      });
    });
  }

  hasChild = (_: number, node: Requirment) => !!node.children && node.children.length > 0;
}

export interface IFormat {
  type: string;
  extension: string;
}

export interface ISupportedFormatResponse {
  supportedFormats: ISupportedFormat;
  _links: Links;
}

export interface ISupportedFormat {
  ['application/vnd.openxmlformats-officedocument.wordprocessingml.document']: string;
  ['application/vnd.oasis.opendocument.text']: string;
  ['text/html']: string;
  ['application/pdf']: string;
  ['application/rtf']: string;
  ['text/asciidoc']: string;
  ['text/markdown']: string;
}

export interface IConcurrencyDetails {
  url: string;
  patch: IPatchObject[];
  token: string;
}

export interface IPatchObject {
  op: string;
  path: string;
  value: any;
}

// Needed for tree structure
export class Requirment {
  name: string;
  id: number;
  children?: Requirment[];
}

const TREE_DATA: Requirment[] = [];
