import {Component, Inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserData} from '../models/UserData.model';
import {projectFunctionality} from '../models/projectFunctionality.model';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {statusPageData} from '../models/statusPageData.model';
import {TranslateService} from '@ngx-translate/core';
import {ProjectRequirment} from '../models/ProjectRequirment.model';
import {User} from '../models/User';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

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
  private sideBarOpen: boolean;
  private currentReq: projectFunctionality;
  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private projectLink: string;
  private mainData: projectFunctionality[];
  private newReqPriority: string;
  private selectedTab: number;
  private maxID: number;
  private statusPage: boolean;
  private statpageData: statusPageData;

  private treeControl: NestedTreeControl<Requirment>;
  private dataSource: MatTreeNestedDataSource<Requirment>;
  private statusbarData: projectFunctionality[];
  private nav: Requirment[];
  private dialog: MatDialog;
  private loading: boolean;

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
    translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
    translate.setDefaultLang('Bokmål');
  }

  enterUserEdit() {
    this.userData.nav = 'userEdit';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/userEdit']);
  }

  ngOnInit() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    this.projectLink = this.userData.currentProject._links.function.href;
    console.log(this.userData.currentProject._links.function.href);
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
      console.log(result);
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
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
              }
            )
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
                  calls--;
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
                        calls--;
                        // @ts-ignore
                        tertiary.referenceProjectRequirement = this.fixRequirmentsText(result._embedded.projectRequirements);
                        if (calls === 0) {
                          this.crunchGatheredData(newlyLoaded);
                        }
                        // tslint:disable-next-line:no-shadowed-variable
                      }, error => {
                        console.error(error);
                      });
                    }
                    if (this.maxID < tertiary.projectFunctionalityId) {
                      this.maxID = tertiary.projectFunctionalityId;
                    }
                  }
                  // If this was the last call exits to next step
                  if (calls === 0) {
                    this.crunchGatheredData(newlyLoaded);
                  }
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
                  calls--;
                  // @ts-ignore
                  secondary.referenceProjectRequirement = this.fixRequirmentsText(result._embedded.projectRequirements);
                  if (calls === 0) {
                    this.crunchGatheredData(newlyLoaded);
                  }
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
            // If this was the last call exits to next step
            if (calls === 0) {
              this.crunchGatheredData(newlyLoaded);
            }
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
            calls--;
            // @ts-ignore
            prime.referenceProjectRequirement = this.fixRequirmentsText(result._embedded.projectRequirements);
            if (calls === 0) {
              this.crunchGatheredData(newlyLoaded);
            }
            // tslint:disable-next-line:no-shadowed-variable
          }, error => {
            console.log(error);
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

  downloadDocument() {
    console.log(this.userData.currentProject);
    this.http.get(this.userData.currentProject._links.document.href, {
      headers: new HttpHeaders({
          Authorization: 'Bearer' + this.userData.oauthClientSecret
        }
      ),
      responseType: 'blob'
    }).subscribe(result => {
      const url = window.URL.createObjectURL(result);
      const a = document.createElement('a');
      a.setAttribute('style', 'display:none;');
      a.href = url;
      document.body.appendChild(a);
      a.download = this.userData.currentProject.projectName + '.docx';
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
    console.log(this.mainData);
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
      this.changeReq(this.currentReq.projectFunctionalityId);
    }
    this.statusBarInfo();
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
      console.log(error);
    });
    this.router.navigate(['/']);
    location.reload();
  }

  /**
   * changeReq
   *
   * Takes the ID of a projectFunctionality and jumps to that functionality, used to select between different parts of the project
   *
   * @param id
   * Wich id to change to
   */
  changeReq(id: number) {
    if (id === 0) { // Loads the statuspage
      this.statusPage = !this.statusPage;
      this.statPageLoad();
    } else {
      this.currentReq = this.findReq(id);
      this.newReqPriority = 'O';
      this.selectedTab = 0;
      this.statusBarInfo();
      this.statusPage = false;
    }
  }

  /**
   * findReq
   *
   * Finds the requirment inn main data with the specified id
   * @param id
   * Wich id to look for
   */
  findReq(id: number): projectFunctionality {
    // Primary level
    for (const primary of this.mainData) {
      if (primary.referenceChildProjectFunctionality.length > 0) {
        // Secondary level
        for (const secondary of primary.referenceChildProjectFunctionality) {
          if (secondary.referenceChildProjectFunctionality !== undefined) {
            // Tertiary level
            for (const tertiary of secondary.referenceChildProjectFunctionality) {
              if (tertiary.projectFunctionalityId === id) {
                return  tertiary;
              }
            }
          } else if (secondary.projectFunctionalityId === id) {
            return secondary;
          }
        }
      } else if (primary.projectFunctionalityId === id) {
        return  primary;
      }
    }
    return null;
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

    const patchString = '[{ "op": "replace", "path": "/requirementText", "value": "' +
      req.requirementText + '"}]';

    this.sendPatch(patchString, req._links.self.href);
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

    const patchString = '[{ "op": "replace", "path": "/processed", "value": ' +
      functionality.processed + '}]';

    this.sendPatch(patchString, functionality._links.self.href);

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
    const patchString = '[{ "op": "replace", "path": "/priority", "value": "' +
      priority + '"}]';

    this.sendPatch(patchString, this.currentReq.referenceProjectRequirement[index]._links.self.href);
  }

  /**
   * sendPatch
   *
   * Sends patches to the server, used by all update functions
   *
   * @param patchString
   * Inputted patchstring to send
   *
   * @param url
   * Inputted url to send patchstring to
   */
  sendPatch(patchString: string, url: string) {
    console.log(patchString);
    this.http.patch(url, JSON.parse(patchString), {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // console.log(result);
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.error(error);
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
    console.log(this.currentReq.referenceProjectRequirement[index]);
    const dialogref = this.dialog.open(DeleteRequirmentDialog, {
      width: '300px'
    });

    dialogref.afterClosed().subscribe(result => {
      console.log(this.currentReq.referenceProjectRequirement[index]._links.self.href);
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
      // this.loadRequirment();
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
      switchto = this.findReq(id);
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
      switchto = this.findReq(id);
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
    this.statpageData.finished = true;
    // Cheks if the project is finished, i could have used the values from progress instead, but this felt more reliable
    for (const req of this.mainData) {
      if ( !req.processed ) {
        this.statpageData.finished = false;
      }
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
      console.log(this.mainData);
    } else {
      this.statpageData.progress = 100;

      // Calls a post to generate document if download is finnished
      this.statpageData.generatingDocument = true;
      this.http.post(this.userData.currentProject._links.document.href, null, {
        headers: new HttpHeaders({
            Authorization: 'Bearer ' + this.userData.oauthClientSecret
          }
        )
      }).subscribe(result => {
        this.statpageData.generatingDocument = false;
        // tslint:disable-next-line:no-shadowed-variable
      }, error => {
        console.log(error);
        alert(error);
      });
    }
    this.statpageData.loaded = true;
  }

  openShareMenu() {
    this.dialog.open(ShareMenu, {
      data: this.userData,
      width: '500px'
    });
  }

  hasChild = (_: number, node: Requirment) => !!node.children && node.children.length > 0;
}

// Needed for tree structure
export class Requirment {
  name: string;
  id: number;
  children?: Requirment[];
}

const TREE_DATA: Requirment[] = [];
@Component({
  // tslint:disable-next-line:component-selector
  selector: 'DeleteRequirment.Dialog',
  templateUrl: '../Modals/RemoveReq.Dialog.html'
})
// tslint:disable-next-line:component-class-suffix
export class DeleteRequirmentDialog {
  constructor(public dialogRef: MatDialogRef<DeleteRequirmentDialog>, @Inject(MAT_DIALOG_DATA) public data: boolean) {
    this.data = true;
  }

  onNoClick() {
    this.dialogRef.close();
  }
}

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'ShareMenu.Dialog',
  templateUrl: '../Modals/ShareMenu.Dialog.html',
  styleUrls: [
    './kravEdit.component.css',
    '../common.css'
  ]
})
// tslint:disable-next-line:component-class-suffix
export class ShareMenu {
  private http: HttpClient;
  private userData: UserData;
  private shares: User[];
  private formgroup: FormGroup;
  private newShare: string;

  constructor(public dialogRef: MatDialogRef<DeleteRequirmentDialog>, @Inject(MAT_DIALOG_DATA) public data: UserData, http: HttpClient, private formBuilder: FormBuilder) {
    this.userData = data;
    this.http = http;
    this.shares = [];
    this.getActiveShares();

    this.formgroup = formBuilder.group({
      email: [this.newShare, [
        Validators.required,
        Validators.email
      ]],
    });
  }

  getActiveShares() {
    this.http.get(this.userData.currentProject._links.access.href, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // @ts-ignore
      this.shares = result._embedded.users;
      console.log(result);
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      console.error(error);
    });
  }

  addShare() {
    this.http.post(this.userData.currentProject._links.share.href.replace('user_email_address', this.newShare), {}, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      this.getActiveShares();
    }, error => {
      console.error(error);
    });
  }

  revokeShare(user: string) {
    this.http.delete(this.userData.currentProject._links.share.href.replace('user_email_address', user), {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      this.getActiveShares();
    }, error => {
      console.error(error);
    });
  }

  onNoClick() {
    this.dialogRef.close();
  }
}
