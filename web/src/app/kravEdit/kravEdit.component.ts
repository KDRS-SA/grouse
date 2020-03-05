import {Component, Inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {UserData} from '../models/UserData.model';
// @ts-ignore
import {convertFromLegacy, REL_FUNCTIONALITY, REL_PROJECT} from '../common';
import {projectFunctionality} from '../models/projectFunctionality.model';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {statusPageData} from '../models/statusPageData.model';
import {TranslateService} from '@ngx-translate/core';

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

  constructor(http: HttpClient, router: Router, dialog: MatDialog, public translate: TranslateService) {
    this.http = http;
    this.router = router;
    this.projectLink = '';
    this.sideBarOpen = false;
    this.dialog = dialog;
    this.statusbarData = [];
    this.statusPage = false;
    this.statpageData = new statusPageData();
    translate.addLangs(['no', 'en', 'ny']);
    translate.setDefaultLang('no');
  }

  ngOnInit() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    this.projectLink = this.userData.currentProject._links.funksjon.href;
    this.fetchMainData();

    this.treeControl = new NestedTreeControl<Requirment>(
      node => node.children
    );

    this.dataSource = new MatTreeNestedDataSource<Requirment>();
    this.dataSource.data = TREE_DATA;
  }

  fetchMainData() {
    this.http.get(this.projectLink, {
      headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        }
      )
    }).subscribe(result => {
      const newlyLoaded = (this.mainData === undefined);
      // @ts-ignore
      this.mainData = result;
      this.convertLegacyLinks();
      if (newlyLoaded) {
        this.currentReq = this.mainData[0].referenceChildProjectFunctionality[0];
        this.selectedTab = 0;
      } else {
        this.changeReq(this.currentReq.projectFunctionalityId);
      }
      this.statusBarInfo();
      console.log(this.mainData);
    }, error => {
      console.error(error);
    });
  }

  /*
  * This method sends the user to the main menu when called
  */
  goToMainMenu() {
    this.userData.nav = 'Menu';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/Menu']);
  }

  /*
  * This method converts the links subsection of elements sent from the server from the old Spring Boot Standard to the new format
  * This might not be required in newer version so this method might disapear later wich whould be a nice thing
  */
  convertLegacyLinks() {
    const NavData: Requirment[] = [];     // For the sidenav Tree every NavReq is fo this purpose aswell
    let maxID = 0;
    for (const prime of this.mainData) {
      const NavReqP = new Requirment();
      NavReqP.id = prime.projectFunctionalityId;
      NavReqP.name = prime.title;
      NavReqP.children = [];

      // For MaxID
      if (NavReqP.id > maxID) {
        maxID = NavReqP.id;
      }

      for (const secondary of prime.referenceChildProjectFunctionality) {
        const NavReqS = new Requirment();
        NavReqS.id = secondary.projectFunctionalityId;
        NavReqS.name = secondary.title;
        NavReqS.children = [];

        // For MaxID
        if (NavReqS.id > maxID) {
          maxID = NavReqS.id;
        }

        for (const tertiary of secondary.referenceChildProjectFunctionality) {
          // @ts-ignore
          tertiary._links = convertFromLegacy(tertiary.links);
          // @ts-ignore
          tertiary.links = null;

          // For the navigation tree
          const NavReqT = new Requirment();
          NavReqT.id = tertiary.projectFunctionalityId;
          NavReqT.name = tertiary.title;
          NavReqS.children.push(NavReqT);

          // For MaxID
          if (NavReqT.id > maxID) {
            maxID = NavReqT.id;
          }
        }
        NavReqP.children.push(NavReqS);
        for (const tertiary of secondary.referenceProjectRequirement) {
          // @ts-ignore
          tertiary._links = convertFromLegacy(tertiary.links);
          // @ts-ignore
          tertiary.links = null;

        }
        // @ts-ignore
        secondary._links = convertFromLegacy(secondary.links);
        // @ts-ignore
        secondary.links = null;
      }
      for (const secondary of prime.referenceProjectRequirement) {
        /*
        for (const tertiary of secondary.referenceChildProjectFunctionality) {
          // @ts-ignore
          tertiary._links = convertFromLegacy(tertiary.links);
          // @ts-ignore
          tertiary.links = null;
        }
        for (const tertiary of secondary.referenceProjectRequirement) {
          // @ts-ignore
          tertiary._links = convertFromLegacy(tertiary.links);
          // @ts-ignore
          tertiary.links = null;
        }
        */
        // @ts-ignore
        secondary._links = convertFromLegacy(secondary.links);
        // @ts-ignore
        secondary.links = null;
      }
      // @ts-ignore
      prime._links = convertFromLegacy(prime.links);
      // @ts-ignore
      prime.links = null;
      NavData.push(NavReqP);
    }
    this.dataSource.data = NavData;
    this.maxID = maxID;
  }

  /*
  *This method both sends a call to the server to invalidate the current auth-token and sends the user to the login page
  * As this method is duplicated it will proabobly be moved later
  */
  logout() {
    localStorage.clear();
    this.http.get(this.userData.logoutAdress, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
    }, error => {
      console.log(error);
    });
    this.router.navigate(['/']);
    location.reload();
  }

  /*
  * Takes the ID of a projectFunctionality and jumps to that functionality, used to select between different parts of the project
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
          if (secondary.referenceChildProjectFunctionality.length > 0) {
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
   * handles a change in requirement text.
   */
  updateFunctionalityProcessed(functionality: projectFunctionality) {
    functionality.processed = !functionality.processed;

    const patchString = '[{ "op": "replace", "path": "/processed", "value": "' +
      functionality.processed + '"}]';

    this.sendPatch(patchString, functionality._links.self.href);

    // Cheks to se if all children of current parent has been processed unless this is a prime
    const parent = this.findParentReq(functionality);
    if (parent !== undefined && parent !== null) {
      let check = true;
      for (const child of parent.referenceChildProjectFunctionality) {
        if (!child.processed) {
          check = false;
        }
      }
      if (check !== parent.processed) {
        this.updateFunctionalityProcessed(parent);
      }
    }
  }

  /**
   * updateRequirementPriority
   *
   * handles a change in requirement priority.
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
   */
  sendPatch(patchString: string, url: string) {
    this.http.patch(url, JSON.parse(patchString), {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // console.log(result);
    }, error => {
      console.error(error);
    });
  }

  addRequirment() {
    const textfield = document.getElementById('NyttKrav');

    this.http.post(
      this.currentReq._links.self.href,
      {
        // @ts-ignore
        requirementText: textfield.value,
        priority: this.newReqPriority
      }, {
      headers: new HttpHeaders({
        Authorization: 'Bearer ' + this.userData.oauthClientSecret
      })
    }).subscribe(result => {
      // @ts-ignore
      textfield.value = null;
      this.fetchMainData();
    }, error => {
      console.error(error);
    });
  }

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
            }, error => console.error(error));
      }
    });
  }

  newReqPriorityChange(priority: string) {
    this.newReqPriority = priority;
  }

  /**
   * nextReq
   *
   * Switches to the next rquirment
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
   * findParentReq
   *
   * finds a parent of the given child, returns null if none
   *
   * @param child of wich the parent should be found
   */
  findParentReq(child: projectFunctionality): projectFunctionality {
    if (child === null) {
      return null;
    }
    // Primary level
    for (const primary of this.mainData) {
      if (primary.projectFunctionalityId === child.projectFunctionalityId) {
        return  null;
      }
      if (primary.referenceChildProjectFunctionality.length > 0) {
        // Secondary level
        for (const secondary of primary.referenceChildProjectFunctionality) {
          if (secondary.projectFunctionalityId === child.projectFunctionalityId) {
            return primary;
          }
          if (secondary.referenceChildProjectFunctionality.length > 0) {
            // Tertiary level
            for (const tertiary of secondary.referenceChildProjectFunctionality) {
              if (tertiary.projectFunctionalityId === child.projectFunctionalityId) {
                return  secondary;
              }
            }
          }
        }
      }
    }
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
    } else {
      this.statpageData.progress = 100;
    }
    this.statpageData.loaded = true;
  }

  /**
   * isPrime
   * Checks wheter or not the given project functionality is from the primary array i.o.w is not a child of anyone
   * @param inn
   * The projectfunctionality to check
   */
  isPrime(inn: projectFunctionality): boolean {
    for (const prime of this.mainData) {
      if (prime === inn) {
        return true;
      }
    }
    return false;
  }

  /**
   * isPrime
   * Checks wheter or not the given project functionality is from the primary array i.o.w is not a child of anyone
   * @param inn
   * ID of the projectfunctionality to check
   */
  isPrimeID(ID: number): boolean {
    for (const prime of this.mainData) {
      if (prime.projectFunctionalityId === ID) {
        return true;
      }
    }
    return false;
  }

  hasChild = (_: number, node: Requirment) => !!node.children && node.children.length > 0;
}

// Needed for tree structure
export class Requirment {
  name: string;
  id: number;
  children?: Requirment[];
}

const TREE_DATA: Requirment[] = [
  {
    name: 'Generelle krav',
    id: 0,
    children: [
      {name: '1.1 Krav om pølser', id: 0},
      {name: '1.2 Krav i henhold til nasjonale lover for saltmengde i kjøttprodukter', id: 0},
      {name: '1.3 Krav i forhold til utnyttelse av lovhull for å ungå krav 1.2', id: 0}
    ]
  },
  {
    name: 'Avanserte krav',
    id: 0,
    children: [
      {
        name: '1',
        id: 0,
        children: [
          { name: '1.1 Krav i forhold til salting', id: 0},
          { name: '1.2 Krav om fri på mandager', id: 0},
          { id: 0, name: '1.3 Krav om sene kvelder'}
        ]
      },
      {
        name: '2',
        id: 0,
        children: [
          { name: '2.1 Krav om bruk av toaletter', id: 0},
          { name: '2.2 Krav om kråker og andre irriterende skapninger', id: 0},
          { name: '2.3 Krav ved valg av ny Dungeon Master for kontorets søndagskvelder', id: 0}
        ]
      }
    ]
  }
];
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
