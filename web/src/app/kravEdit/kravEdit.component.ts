import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {UserData} from '../models/UserData.model';
// @ts-ignore
import {convertFromLegacy, REL_FUNCTIONALITY, REL_PROJECT} from '../common';
import {projectFunctionality} from '../models/projectFunctionality.model';
import {NestedTreeControl} from '@angular/cdk/tree';
import {MatTreeNestedDataSource} from '@angular/material';

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
  private currentReq;

  private http: HttpClient;
  private router: Router;
  private userData: UserData;
  private projectLink: string;
  private mainData: projectFunctionality[];

  private treeControl: NestedTreeControl<Requirment>;
  private dataSource: MatTreeNestedDataSource<Requirment>;
  private nav: Requirment[];

  constructor(http: HttpClient, router: Router) {
    this.http = http;
    this.router = router;
    this.projectLink = '';
    this.sideBarOpen = false;
  }

  ngOnInit() {
    this.userData = JSON.parse(localStorage.getItem('UserData'));
    this.projectLink = this.userData.currentProject._links.funksjon.href;
    console.log(this.projectLink);
    this.http.get(this.projectLink, {
      headers: new HttpHeaders({
          Authorization: 'Bearer ' + this.userData.oauthClientSecret
        }
      )
    }).subscribe(result => {
      // @ts-ignore
      this.mainData = result;
      this.convertLegacyLinks();
      this.currentReq = this.mainData[0].referenceChildProjectFunctionality[0];
    }, error => {
      console.error(error);
    });

    this.treeControl = new NestedTreeControl<Requirment>(
      node => node.children
    );

    this.dataSource = new MatTreeNestedDataSource<Requirment>();
    this.dataSource.data = TREE_DATA;
  }

  goToMainMenu() {
    this.userData.nav = 'Menu';
    localStorage.setItem('UserData', JSON.stringify(this.userData));
    this.router.navigate(['/Menu']);
  }

  convertLegacyLinks() {
    const NavData: Requirment[] = [];     // For the sidenav Tree every NavReq is fo this purpose aswell
    for (const prime of this.mainData) {
      const NavReqP = new Requirment();
      NavReqP.id = prime.projectFunctionalityId;
      NavReqP.name = prime.title;
      NavReqP.children = [];
      for (const secondary of prime.referenceChildProjectFunctionality) {
        const NavReqS = new Requirment();
        NavReqS.id = secondary.projectFunctionalityId;
        NavReqS.name = secondary.title;
        NavReqS.children = [];
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
  }

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

  changeReq(id: number) {
    console.log(id);
    this.sideBarOpen = false;
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
]
