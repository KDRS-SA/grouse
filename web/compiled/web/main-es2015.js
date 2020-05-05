(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./$$_lazy_route_resource lazy recursive":
/*!******************************************************!*\
  !*** ./$$_lazy_route_resource lazy namespace object ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Admin/Admin.component.html":
/*!**********************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Admin/Admin.component.html ***!
  \**********************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<mat-toolbar class=\"flex-row-between\" color=\"primary\" style=\"padding: 0\">\r\n  <div class=\"flex-row-left\">\r\n    <div class=\"flex-row-evenlyer\">\r\n      <button class=\"no-p\" mat-button (click)=\"goToMainMenu()\">\r\n        <div class=\"flex-row-left no-m\">\r\n          <div class=\"flex-row-evenlyer no-m\">\r\n            <div class=\"flex-column flex-center\">\r\n              <div class=\"Grouse-Avatar\"></div>\r\n            </div>\r\n            <p>Grouse</p>\r\n          </div>\r\n        </div>\r\n      </button>\r\n    </div>\r\n  </div>\r\n  <div class=\"flex-row-right\">\r\n    <div class=\"flex-row-between\">\r\n      <button mat-button (click)=\"enterUserEdit()\">\r\n        <div class=\"flex-row-between\">\r\n          <div class=\"flex-center\">\r\n            <i class=\"material-icons flex-center navbar-icon icon-large\">account_box</i>\r\n          </div>\r\n          <p>{{this.userData.userName}}</p>\r\n        </div>\r\n      </button>\r\n      <button mat-button (click)=\"logout()\">\r\n        <div class=\"flex-column\">\r\n          <i class=\"material-icons flex-center navbar-icon\">exit_to_app</i>\r\n          <p class=\"no-m\">{{'KravEdit.Logout' | translate}}</p>\r\n        </div>\r\n      </button>\r\n    </div>\r\n  </div>\r\n</mat-toolbar>\r\n<div class=\"main-div\">\r\n  <div class=\"secondary-div\">\r\n\r\n    <mat-card>\r\n      <!--List of users with search functionality-->\r\n      <mat-tab-group [selectedIndex]=\"usermode ? 0 : 1\">\r\n        <mat-tab label=\"{{'Admin.Users' | translate}}\">\r\n          <mat-form-field class=\"w-70 search-bar m-lr-10 m-t-10\">\r\n            <mat-label>{{'Admin.Search' | translate}}</mat-label>\r\n            <input type=\"email\" matInput [(ngModel)]=\"searchUser\" placeholder=\"{{'Admin.SearchDescPUsers' | translate}}\" (change)=\"getListOfUsers()\">\r\n          </mat-form-field>\r\n          <mat-divider></mat-divider>\r\n          <div class=\"list-div\">\r\n            <mat-list>\r\n              <div *ngFor=\"let user of listOfUsers\">\r\n                <button mat-button class=\"w-100\" (click)=\"selectThisUser(user)\">\r\n                  <mat-list-item class=\"w-100 no-p\">\r\n                    <div class=\"w-100 flex-row-between\">\r\n                      <p>{{user.username}}</p>\r\n                      <i *ngIf=\"selectedUser === user\" class=\"material-icons flex-center navbar-icon\">check</i>\r\n                    </div>\r\n                    <mat-divider></mat-divider>\r\n                  </mat-list-item>\r\n                </button>\r\n              </div>\r\n            </mat-list>\r\n          </div>\r\n        </mat-tab>\r\n\r\n        <!--List of projects with search functionality-->\r\n        <mat-tab label=\"{{'Admin.Projects' | translate}}\">\r\n          <mat-form-field class=\"w-70 search-bar m-lr-10 m-t-10\">\r\n            <mat-label>{{'Admin.Search' | translate}}</mat-label>\r\n            <input type=\"text\" matInput [(ngModel)]=\"searchProject\" placeholder=\"{{'Admin.SearchDescProjects' | translate}}\" (change)=\"getListOfProjects()\">\r\n          </mat-form-field>\r\n          <mat-divider></mat-divider>\r\n          <div class=\"list-div\">\r\n            <mat-list>\r\n              <div *ngFor=\"let project of listOfProjects\">\r\n                <button mat-button class=\"w-100\" (click)=\"selectThisProject(project)\">\r\n                  <mat-list-item class=\"w-100 no-p\">\r\n                    <div class=\"w-100 flex-row-between\">\r\n                      <p>{{project.projectName}}</p>\r\n                      <i *ngIf=\"selectedProject === project\" class=\"material-icons flex-center navbar-icon\">check</i>\r\n                    </div>\r\n                    <mat-divider></mat-divider>\r\n                  </mat-list-item>\r\n                </button>\r\n              </div>\r\n            </mat-list>\r\n          </div>\r\n        </mat-tab>\r\n      </mat-tab-group>\r\n    </mat-card>\r\n  </div>\r\n  <div class=\"secondary-div\">\r\n    <mat-card>\r\n      <mat-card-header>\r\n        <h3>{{'Admin.Details' | translate}}</h3>\r\n      </mat-card-header>\r\n      <mat-card-content>\r\n        <!--Showing user details-->\r\n        <div *ngIf=\"this.usermode\" class=\"details\">\r\n          <!--User info-->\r\n          <div *ngIf=\"selectedUser !== undefined\">\r\n            <div>\r\n              <mat-list>\r\n                <mat-list-item>\r\n                  <div class=\"flex-row-between w-100\">\r\n                    <p>{{'Admin.UsernameLabel' | translate}}</p>\r\n                    <p>{{selectedUser.username}}</p>\r\n                  </div>\r\n                  <mat-divider></mat-divider>\r\n                </mat-list-item>\r\n                <mat-list-item>\r\n                  <div class=\"flex-row-between w-100\">\r\n                    <p>{{'Admin.UserActiveLabel' | translate}}</p>\r\n                    <p *ngIf=\"selectedUser.accountNonExpired\">{{'Admin.Yes' | translate}}</p>\r\n                    <p *ngIf=\"!selectedUser.accountNonExpired\">{{'Admin.No' | translate}}</p>\r\n                  </div>\r\n                  <mat-divider></mat-divider>\r\n                </mat-list-item>\r\n                <mat-list-item>\r\n                  <div class=\"flex-row-between w-100\">\r\n                    <p>{{'Admin.PasswordExpired' | translate}}</p>\r\n                    <p *ngIf=\"!selectedUser.accountNonExpired\">{{'Admin.Yes' | translate}}</p>\r\n                    <p *ngIf=\"selectedUser.accountNonExpired\">{{'Admin.No' | translate}}</p>\r\n                  </div>\r\n                  <mat-divider></mat-divider>\r\n                </mat-list-item>\r\n                <mat-list-item>\r\n                  <div class=\"flex-row-between w-100\">\r\n                    <p>{{'Admin.UserExpired' | translate}}</p>\r\n                    <p *ngIf=\"!selectedUser.accountNonExpired\">{{'Admin.Yes' | translate}}</p>\r\n                    <p *ngIf=\"selectedUser.accountNonExpired\">{{'Admin.No' | translate}}</p>\r\n                  </div>\r\n                  <mat-divider></mat-divider>\r\n                </mat-list-item>\r\n                <mat-list-item>\r\n                  <div class=\"flex-row-between w-100\">\r\n                    <p>{{'Admin.DeleteUserLabel' | translate}}</p>\r\n                    <button mat-raised-button color=\"warn\" class=\"h-80 m-t-10\" (click)=\"deleteUser()\">{{'Admin.DeleteUser' | translate}}</button>\r\n                  </div>\r\n                </mat-list-item>\r\n                <!--List of projects for this user-->\r\n                <mat-label>{{'Admin.OwnedProjectsLabel' | translate}}</mat-label>\r\n                <div class=\"projects-and-users-minibox\">\r\n                  <button mat-raised-button class=\"box-button\" *ngFor=\"let project of filterProjectsByUser()\" (click)=\"selectThisProject(project)\">\r\n                    {{project.projectName}}\r\n                  </button>\r\n                </div>\r\n              </mat-list>\r\n            </div>\r\n          </div>\r\n          <!--If no user is selected-->\r\n          <div *ngIf=\"selectedUser === undefined\" class=\"flex-center flex-column\">\r\n            <p>{{'Admin.PleaseSelectUserTooltip' | translate}}</p>\r\n          </div>\r\n        </div>\r\n        <!--Showing project details-->\r\n        <div *ngIf=\"!this.usermode && searchProject !== undefined\" class=\"details\">\r\n          <mat-list>\r\n            <mat-list-item>\r\n              <div class=\"flex-row-between w-100\">\r\n                <p>{{'Admin.ProjectIdLabel' | translate}}</p>\r\n                <p>{{selectedProject.projectId}}</p>\r\n              </div>\r\n              <mat-divider></mat-divider>\r\n            </mat-list-item>\r\n            <mat-list-item>\r\n              <div class=\"flex-row-between w-100\">\r\n                <p>{{'Admin.ProjectNameLabel' | translate}}</p>\r\n                <p>{{selectedProject.projectName}}</p>\r\n              </div>\r\n              <mat-divider></mat-divider>\r\n            </mat-list-item>\r\n            <mat-list-item>\r\n              <div class=\"flex-row-between w-100\">\r\n                <p>{{'Admin.ProjectCreatedLabel' | translate}}</p>\r\n                <p>{{selectedProject.createdDate}}</p>\r\n              </div>\r\n              <mat-divider></mat-divider>\r\n            </mat-list-item>\r\n            <mat-list-item>\r\n              <div class=\"flex-row-between w-100\">\r\n                <p>{{'Admin.LastModifiedLabel' | translate}}</p>\r\n                <p>{{selectedProject.lastModifiedDate}}</p>\r\n              </div>\r\n            </mat-list-item>\r\n            <mat-label class=\"m-t-14\">{{'Admin.UsersWithAccessLabel' | translate}}</mat-label>\r\n            <div class=\"projects-and-users-minibox project-box\">\r\n              <mat-list class=\"w-100\">\r\n                <mat-list-item *ngFor=\"let user of usersOfThisProject\">\r\n                  <div class=\"w-100 flex-row-between\">\r\n                    <!--Label-->\r\n                    <div class=\"p-20\">\r\n                      <mat-label *ngIf=\"user.username === selectedProject.ownedBy\">{{'Admin.OwnerLabel' | translate}}</mat-label>\r\n                      <mat-label *ngIf=\"user.username !== selectedProject.ownedBy\">{{'Admin.SharedLabel' | translate}}</mat-label>\r\n                    </div>\r\n                    <div class=\"p-10\">\r\n                      <!--User button-->\r\n                      <button (click)=\"selectThisUserName(user.username)\" mat-raised-button class=\"w-100\">\r\n                        {{user.username}}\r\n                      </button>\r\n                    </div>\r\n                    <div class=\"p-10\">\r\n                      <!--Red button-->\r\n                      <button *ngIf=\"user.username === selectedProject.ownedBy\" mat-flat-button color=\"warn\" (click)=\"deleteProject()\">{{'Admin.DeleteProject' | translate}}</button>\r\n                      <button *ngIf=\"user.username !== selectedProject.ownedBy\" mat-flat-button color=\"warn\" (click)=\"revokeShare(user)\">{{'Admin.RevokeShare' | translate}}</button>\r\n                    </div>\r\n                  </div>\r\n                  <mat-divider></mat-divider>\r\n                </mat-list-item>\r\n              </mat-list>\r\n            </div>\r\n          </mat-list>\r\n        </div>\r\n      </mat-card-content>\r\n    </mat-card>\r\n  </div>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/KravEdit/kravEdit.component.html":
/*!****************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/KravEdit/kravEdit.component.html ***!
  \****************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<mat-toolbar class=\"flex-row-between\" color=\"primary\" style=\"padding: 0\">\r\n  <div class=\"flex-row-left\">\r\n    <div class=\"flex-row-evenlyer\">\r\n      <button class=\"no-p\" mat-button (click)=\"goToMainMenu()\">\r\n        <div class=\"flex-row-left no-m\">\r\n          <div class=\"flex-row-evenlyer no-m\">\r\n            <div class=\"flex-column flex-center\">\r\n              <div class=\"Grouse-Avatar\"></div>\r\n            </div>\r\n            <p>Grouse</p>\r\n          </div>\r\n        </div>\r\n      </button>\r\n    </div>\r\n  </div>\r\n  <div class=\"flex-row-right\">\r\n    <div class=\"flex-row-between\">\r\n      <button *ngIf=\"userData.currentProject.ownedBy === userData.userName\" mat-button matTooltip=\"{{'KravEdit.ShareProjectTooltip' | translate}}\" (click)=\"openShareMenu()\">\r\n        <span class=\"material-icons\">share</span>\r\n      </button>\r\n      <!--User Edit/Adminstration entering-->\r\n      <div *ngIf=\"userData._links['project-list-all'] === undefined\">\r\n        <button mat-button (click)=\"enterUserEdit()\">\r\n          <div class=\"flex-row-between\">\r\n            <div class=\"flex-center\">\r\n              <i class=\"material-icons flex-center navbar-icon icon-large\">account_box</i>\r\n            </div>\r\n            <p>{{this.userData.userName}}</p>\r\n          </div>\r\n        </button>\r\n      </div>\r\n      <div *ngIf=\"userData._links['project-list-all'] !== undefined\">\r\n        <button mat-button (click)=\"enterAdminPage()\">\r\n          <div class=\"flex-row-between\">\r\n            <p>Admin</p>\r\n            <div class=\"flex-center\">\r\n              <i class=\"material-icons flex-center navbar-icon icon-large\">supervisor_account</i>\r\n            </div>\r\n          </div>\r\n        </button>\r\n        <button mat-button class=\"no-p\" (click)=\"enterUserEdit()\">\r\n          <p>{{this.userData.userName}}</p>\r\n        </button>\r\n      </div>\r\n      <button mat-button (click)=\"logout()\">\r\n        <div class=\"flex-column\">\r\n          <i class=\"material-icons flex-center navbar-icon\">exit_to_app</i>\r\n          <p class=\"no-m\">{{'KravEdit.Logout' | translate}}</p>\r\n        </div>\r\n      </button>\r\n    </div>\r\n  </div>\r\n</mat-toolbar>\r\n<mat-sidenav-container *ngIf=\"!loading\" class=\"sidebar\" hasBackdrop=\"true\">\r\n  <mat-sidenav mode=\"over\" [opened]=\"sideBarOpen\" class=\"sidenav\">\r\n\r\n    <!-- Tree DONT TOUCH IT FOR THE LOVE OF EVERYTHING HOLY-->\r\n\r\n    <mat-tree [dataSource]=\"dataSource\" [treeControl]=\"treeControl\" class=\"example-tree\">\r\n      <!-- This is the tree node template for leaf nodes -->\r\n      <mat-tree-node *matTreeNodeDef=\"let node\" matTreeNodeToggle>\r\n        <button mat-button class=\"fill no-p\" (click)=\"changeFunctionality(node.id)\">\r\n          <li class=\"mat-tree-node\">\r\n            <!-- use a disabled button to provide padding for tree leaf -->\r\n            {{node.name}}\r\n          </li>\r\n          <mat-divider></mat-divider>\r\n        </button>\r\n      </mat-tree-node>\r\n      <!-- This is the tree node template for expandable nodes -->\r\n      <mat-nested-tree-node *matTreeNodeDef=\"let node; when: hasChild\">\r\n          <li>\r\n            <div class=\"mat-tree-node\">\r\n              <button mat-icon-button matTreeNodeToggle\r\n                      [attr.aria-label]=\"'toggle ' + node.name\">\r\n                <mat-icon class=\"mat-icon-rtl-mirror\">\r\n                  {{treeControl.isExpanded(node) ? 'expand_more' : 'chevron_right'}}\r\n                </mat-icon>\r\n              </button>\r\n              {{node.name}}\r\n            </div>\r\n            <ul [class.example-tree-invisible]=\"!treeControl.isExpanded(node)\">\r\n              <ng-container matTreeNodeOutlet></ng-container>\r\n            </ul>\r\n          </li>\r\n        <mat-divider></mat-divider>\r\n      </mat-nested-tree-node>\r\n    </mat-tree>\r\n    <button *ngIf=\"!statusPage\" class=\"w-100\" mat-button (click)=\"statusPage = true; this.statPageLoad()\">{{'KravEdit.Summary' | translate}}</button>\r\n    <button *ngIf=\"statusPage\" class=\"w-100\" mat-button (click)=\"statusPage = false; sideBarOpen = false\">{{'KravEdit.Document' | translate}}</button>\r\n  </mat-sidenav>\r\n  <mat-sidenav-content class=\"sidenav-content flex-row-between\" (mouseover)=\"sideBarOpen = false\">\r\n    <button mat-raised-button class=\"no-p no-m\" (click)=\"sideBarOpen = !sideBarOpen\">\r\n      <i class=\"material-icons\">\r\n        list\r\n      </i>\r\n    </button>\r\n    <!--The centre column where you edit stuff-->\r\n    <div *ngIf=\"!statusPage && currentReq !== undefined\" class=\"edit-column\">\r\n      <mat-card class=\"Krav-card-edit\">\r\n        <!--Edit part (with the tabs and the text fields)-->\r\n        <mat-tab-group [selectedIndex]=\"selectedTab\" (selectedIndexChange)=\"selectedTab = $event\">\r\n          <mat-tab *ngFor=\"let req of this.currentReq.referenceProjectRequirement; let i = index\" label=\"{{i+1}}\">\r\n            <div class=\"flex-column\">\r\n              <mat-form-field class=\"Krav-form\">\r\n                <mat-label>{{'KravEdit.Requirement' | translate}}</mat-label>\r\n                <textarea id=\"field-{{i}}\" class=\"Krav-input\" matInput value=\"{{req.requirementText}}\" (change)=\"updateRequirementText(i)\"></textarea>\r\n              </mat-form-field>\r\n              <mat-label>{{'KravEdit.Priority' | translate}}</mat-label>\r\n              <div class=\"Krav-options flex-row-between p-10\">\r\n                <mat-button-toggle-group>\r\n                  <mat-button-toggle [checked]=\"req.priority == 'O'\" (click)=\"updateRequirementPriority(i, 'O')\">O</mat-button-toggle>\r\n                  <mat-button-toggle [checked]=\"req.priority == '1'\" (click)=\"updateRequirementPriority(i, '1')\">1</mat-button-toggle>\r\n                  <mat-button-toggle [checked]=\"req.priority == '2'\" (click)=\"updateRequirementPriority(i, '2')\">2</mat-button-toggle>\r\n                  <mat-button-toggle [checked]=\"req.priority == 'O (i)'\" (click)=\"updateRequirementPriority(i, 'O (i)')\">O(i)</mat-button-toggle>\r\n                  <mat-button-toggle [checked]=\"req.priority == '1 (i)'\" (click)=\"updateRequirementPriority(i, '1 (i)')\">1(i)</mat-button-toggle>\r\n                  <mat-button-toggle [checked]=\"req.priority == '2 (i)'\" (click)=\"updateRequirementPriority(i, '2 (i)')\">2(i)</mat-button-toggle>\r\n                </mat-button-toggle-group>\r\n                <br>\r\n                <div>\r\n                  <button matTooltip=\"{{'KravEdit.Delete-warning' | translate}}\" color=\"warn\" mat-raised-button (click)=\"removeRequirment(i)\" class=\"Krav-options-button\"><i class=\"material-icons\">delete_forever</i></button>\r\n                </div>\r\n              </div>\r\n            </div>\r\n          </mat-tab>\r\n          <!--New Requirment-->\r\n          <mat-tab label=\"{{'KravEdit.New' | translate}}\">\r\n            <div class=\"flex-column\">\r\n              <mat-form-field class=\"Krav-form\">\r\n                <mat-label>{{'KravEdit.Requirement' | translate}}</mat-label>\r\n                <textarea class=\"Krav-input\" matInput placeholder=\"{{'KravEdit.New-Req' | translate}}\" id=\"NyttKrav\"></textarea>\r\n              </mat-form-field>\r\n              <mat-label>{{'KravEdit.Priority' | translate}}</mat-label>\r\n              <div class=\"Krav-options flex-row-between p-10\">\r\n                <mat-button-toggle-group id=\"NewReqPriorityButtons\">\r\n                  <mat-button-toggle (click)=\"newReqPriorityChange('O')\" checked>O</mat-button-toggle>\r\n                  <mat-button-toggle (click)=\"newReqPriorityChange('1')\">1</mat-button-toggle>\r\n                  <mat-button-toggle (click)=\"newReqPriorityChange('2')\">2</mat-button-toggle>\r\n                  <mat-button-toggle (click)=\"newReqPriorityChange('O (i)')\">O(i)</mat-button-toggle>\r\n                  <mat-button-toggle (click)=\"newReqPriorityChange('1 (i)')\">1(i)</mat-button-toggle>\r\n                  <mat-button-toggle (click)=\"newReqPriorityChange('2 (i)')\">2(i)</mat-button-toggle>\r\n                </mat-button-toggle-group>\r\n                <br>\r\n                <button mat-raised-button (click)=\"addRequirment()\">{{'KravEdit.Add-Req' | translate}}</button>\r\n              </div>\r\n            </div>\r\n          </mat-tab>\r\n        </mat-tab-group>\r\n      </mat-card>\r\n      <mat-card class=\"Krav-card\">\r\n        <mat-card-header>\r\n          <h2>{{currentReq.title}}</h2>\r\n        </mat-card-header>\r\n        <mat-card-content>\r\n          <h4>{{'KravEdit.Description' | translate}}</h4>\r\n          <div>{{currentReq.description}}</div>\r\n          <h4>{{'KravEdit.Explaination' | translate}}</h4>\r\n          <div>{{currentReq.explanation}}</div>\r\n          <h4>{{'KravEdit.Consequence' | translate}}</h4>\r\n          <div>{{currentReq.consequence}}</div>\r\n        </mat-card-content>\r\n      </mat-card>\r\n    </div>\r\n    <!--Status Page-->\r\n    <div *ngIf=\"statusPage\" class=\"status-page\">\r\n      <mat-card class=\"no-p m-t-10\" style=\"height: 780px\">\r\n        <mat-card-header class=\"flex-row-left\">\r\n          <button mat-raised-button color=\"primary\" (click)=\"this.statusPage = !this.statusPage\"><i class=\"material-icons\">arrow_left</i></button>\r\n          <h1 class=\"m-lr-10\">{{this.userData.currentProject.projectName}}</h1>\r\n        </mat-card-header>\r\n        <!--Loading-->\r\n        <mat-card-content *ngIf=\"!statpageData.loaded\" class=\"flex-column p-10\">\r\n          <mat-progress-bar mode=\"indeterminate\"></mat-progress-bar>\r\n        </mat-card-content>\r\n        <mat-card-content *ngIf=\"statpageData.loaded\" class=\"p-20\">\r\n          <div class=\"flex-column\">\r\n            <h4>{{'KravEdit.Progress' | translate}} {{statpageData.progress}}%</h4>\r\n            <mat-progress-bar mode=\"determinate\" value=\"{{statpageData.progress}}\"></mat-progress-bar>\r\n          </div>\r\n          <mat-divider class=\"m-t-10\"></mat-divider>\r\n          <div class=\"m-t-10\">\r\n            <div class=\"flex-row-between p-10\">\r\n              <h4>{{'KravEdit.Un-finished-req' | translate}} </h4>\r\n              <button mat-button matTooltip=\"{{'KravEdit.Un-finished-req-Tooltip' | translate}}\"><i class=\"material-icons\">help</i></button>\r\n            </div>\r\n            <div class=\"unfinished-box\">\r\n              <button mat-raised-button class=\"color-lightgray unfinished-button\" *ngFor=\"let req of statpageData.unfinished\" matTooltip=\"{{req.title}}\" (click)=\"changeFunctionality(req.projectFunctionalityId); this.statusPage = !this.statusPage\">\r\n                <p class=\"no-m\">{{req.functionalityNumber}}</p>\r\n              </button>\r\n            </div>\r\n          </div>\r\n          <mat-divider></mat-divider>\r\n          <div class=\"flex-center flex-column p-20\" *ngIf=\"statpageData.progress === 100 && !statpageData.generatingDocument\">\r\n            <h4>{{'KravEdit.Document' | translate}}</h4>\r\n            <div class=\"flex-row-left\">\r\n              <div class=\"download-button-container\">\r\n                <div class=\"w-100\">\r\n                  <mat-form-field class=\"w-100\">\r\n                    <mat-label>Format</mat-label>\r\n                    <mat-select>\r\n                      <mat-option *ngFor=\"let format of statpageData.sportedFormats\" [value]=\"format.type\" (click)=\"statpageData.selectedFormat = format\">\r\n                        {{userData.currentProject.projectName}}.{{format.extension}}\r\n                      </mat-option>\r\n                    </mat-select>\r\n                  </mat-form-field>\r\n                </div>\r\n              </div>\r\n              <button class=\"download-button\" [disabled]=\"statpageData.selectedFormat === null\" mat-raised-button (click)=\"downloadDocument()\">Last ned</button>\r\n            </div>\r\n          </div>\r\n          <div class=\"flex-center flex-column p-20\" *ngIf=\"statpageData.progress !== 100 && !statpageData.generatingDocument\">\r\n            <h4>{{'KravEdit.Document' | translate}}</h4>\r\n            <p>{{'KravEdit.NotReady' | translate}}</p>\r\n          </div>\r\n          <div class=\"flex-center flex-column p-20\" *ngIf=\"statpageData.generatingDocument\">\r\n            <h4>{{'KravEdit.Document' | translate}}</h4>\r\n            <mat-progress-bar mode=\"indeterminate\"></mat-progress-bar>\r\n            <p>{{'KravEdit.GeneratingDoc' | translate}}</p>\r\n          </div>\r\n        </mat-card-content>\r\n      </mat-card>\r\n    </div>\r\n    <!--The progress bar that shows the users progress-->\r\n    <div *ngIf=\"!statusPage\" class=\"status-bar flex-column m-lr-10\">\r\n      <mat-card>\r\n        <!--Actions for movment, goes forwards and backwards also aproves the current requirment-->\r\n        <mat-card-actions class=\"nav flex-row\">\r\n          <button matTooltip=\"{{'KravEdit.Previous-req' | translate}}\" mat-raised-button color=\"primary\" (click)=\"previousReq()\">\r\n            <i class=\"material-icons\">arrow_left</i>\r\n          </button>\r\n          <button mat-raised-button matTooltip=\"{{'KravEdit.Accept-Deny' | translate}}\"\r\n            [ngClass]=\"{'mat-primary' : !currentReq.processed, 'color-success' : currentReq.processed}\"\r\n            (click)=\"updateFunctionalityProcessed(currentReq)\">\r\n            <i class=\"material-icons\">done_outline</i>\r\n          </button>\r\n          <button matTooltip=\"{{'KravEdit.Next-req' | translate}}\" mat-raised-button class=\"no-m\" color=\"primary\" (click)=\"nextReq()\">\r\n            <i class=\"material-icons\">arrow_right</i>\r\n          </button>\r\n        </mat-card-actions>\r\n        <!--Status indicator for how far along you are-->\r\n        <mat-card-content>\r\n          <div class=\"flex-center\">\r\n            <div class=\"flex-column flex-center status-bar-button-container\">\r\n              <div *ngFor=\"let req of this.statusbarData\">\r\n                <button mat-raised-button matTooltip=\"{{req.title}}\" (click)=\"changeFunctionality(req.projectFunctionalityId)\" [ngClass]=\"{\r\n                  'mat-accent' : this.currentReq.projectFunctionalityId === req.projectFunctionalityId,\r\n                  'color-success' : req.processed && req.projectFunctionalityId !== this.currentReq.projectFunctionalityId,\r\n                  'color-lightgray' : !req.processed && req.projectFunctionalityId < this.currentReq.projectFunctionalityId,\r\n                  'big-button' : req.projectFunctionalityId === 0 || this.currentReq.projectFunctionalityId === req.projectFunctionalityId\r\n                }\">\r\n                  {{req.functionalityNumber}}\r\n                </button>\r\n              </div>\r\n            </div>\r\n          </div>\r\n        </mat-card-content>\r\n      </mat-card>\r\n    </div>\r\n  </mat-sidenav-content>\r\n</mat-sidenav-container>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Login/GDPRContent.html":
/*!******************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Login/GDPRContent.html ***!
  \******************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<!DOCTYPE html>\r\n<html lang=\"en\">\r\n<head>\r\n    <meta charset=\"UTF-8\">\r\n    <title>GDPR</title>\r\n</head>\r\n<body>\r\n\r\n<h1 mat-dialog-title>GDPR - General Data Protection Regulation</h1>\r\n<mat-dialog-content class=\"mat-typography\">\r\n\r\n<h2>{{'GDPR.User' | translate}}</h2>\r\n  <p>{{'GDPR.UserTXT' | translate}}</p>\r\n\r\n<h2>{{'GDPR.Admin' |translate}}</h2>\r\n  <p>{{'GDPR.AdminTXT' | translate}}</p>\r\n\r\n<h2>{{'GDPR.InfoSave' | translate}}</h2>\r\n  <p>{{'GDPR.InfoSaveTXT' | translate}}</p>\r\n\r\n<h2>{{'GDPR.InfoDelete' | translate}}</h2>\r\n  <p>{{'GDPR.InfoDeleteTXT' | translate}}</p>\r\n  <p>{{'GDPR.OBS!' | translate}}</p>\r\n</mat-dialog-content>\r\n<mat-dialog-actions align=\"end\">\r\n  <button mat-button mat-dialog-close>{{'GDPR.Close' |translate}}</button>\r\n</mat-dialog-actions>\r\n\r\n</body>\r\n</html>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Login/Login.component.html":
/*!**********************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Login/Login.component.html ***!
  \**********************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"Login-Background\"></div>\r\n<div class=\"Login-page flex-center\" style=\"overflow: hidden\">\r\n  <mat-card class=\"login-div\" [@shake]=\"this.shake ? 'end' : ''\">\r\n    <div>\r\n      <div>\r\n        <mat-card-header class=\"flex-center\">\r\n          <div mat-card-avatar class=\"Grouse-Avatar\"></div>\r\n          <mat-card-title>Grouse</mat-card-title>\r\n          <mat-card-subtitle>{{'Login.subtitle' | translate}}</mat-card-subtitle>\r\n          <div class=\"lang-test\">\r\n            <button mat-button [matMenuTriggerFor]=\"langselect\">{{'Language.Language' | translate}}</button>\r\n            <mat-menu #langselect=\"matMenu\" xPosition=\"after\">\r\n              <button mat-menu-item (click)=\"changeLang('Bokmål')\">Bokmål</button>\r\n              <button mat-menu-item (click)=\"changeLang('Nynorsk')\">Nynorsk</button>\r\n              <button mat-menu-item (click)=\"changeLang('English')\">English</button>\r\n            </mat-menu>\r\n          </div>\r\n        </mat-card-header>\r\n      </div>\r\n      <div class=\"flex-row-right\">\r\n      <!--Div to keep all the contents of everything underneath the header of the card,\r\n      the animation happens in this div-->\r\n        <div class=\"login\" [@toggleSlide]=\"login ? 'login' : 'register'\">\r\n          <!--Login Div, contains the text-fields and buttons that are used to sign in-->\r\n          <form  class=\"flex-column\" [formGroup]=\"loginForm\" (ngSubmit)=\"loginSubmit()\" novalidate>\r\n            <mat-card-content class=\"flex-center flex-column\">\r\n              <div style=\"width: 80%\">\r\n                <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                  <mat-label>{{'Login.email' | translate}}</mat-label>\r\n                  <input id=\"loginEmail\" matInput formControlName=\"email\" [(ngModel)]=\"loginUser.email\" required placeholder=\"{{'Login.placeholder-email' |translate}}\">\r\n                  <mat-icon matSuffix>mail</mat-icon>\r\n                  <mat-error *ngIf=\"email.invalid\">{{getErrorMessage()}}</mat-error>\r\n                </mat-form-field>\r\n              </div>\r\n              <div style=\"width: 80%\">\r\n                <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                  <mat-label>{{'Login.password' | translate}}</mat-label>\r\n                  <input id=\"loginPassword\" matInput formControlName=\"password\" [(ngModel)]=\"loginUser.password\" [type]=\"'password'\" placeholder=\"{{'Login.placeholder-pass' | translate}}\">\r\n                  <mat-icon matSuffix>lock</mat-icon>\r\n                </mat-form-field>\r\n              </div>\r\n            </mat-card-content>\r\n\r\n            <mat-card-actions> <!--Button area for Loggin side-->\r\n              <div class=\"button-container\">\r\n                <div class=\"flex-row-right\">\r\n                  <button mat-raised-button type=\"submit\" color=\"primary\"  [disabled]=\"!loginForm.valid\">{{'Login.login' | translate}}</button>\r\n                </div>\r\n                <div class=\"flex-row-right\">\r\n                  <button mat-raised-button type=\"button\" color=\"secondary\" (click)=\"changeMode()\">{{'Login.register' | translate}}</button>\r\n                </div>\r\n              </div>\r\n            </mat-card-actions>\r\n          </form>\r\n        </div>\r\n\r\n        <div class=\"divider\"></div>\r\n\r\n        <div class=\"register\" [@toggleSlide]=\"login ? 'register' : 'login'\">\r\n          <form [formGroup]=\"registerForm\" (ngSubmit)=\"registerForm.valid && registerSubmit()\" novalidate>\r\n          <!--Register Div; contains the text-fields and buttons that are used to sign up-->\r\n            <mat-card-content class=\"flex-center flex-column\">\r\n              <div style=\"width: 80%\">\r\n                <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                  <mat-label>{{'Login.email' | translate}}</mat-label>\r\n                  <input id=\"regEmail\" matInput formControlName=\"email\" [(ngModel)]=\"regUser.email\" required placeholder=\"{{'Login.placeholder-email-reg' | translate}}\">\r\n                  <mat-icon matSuffix>mail</mat-icon>\r\n                  <mat-error *ngIf=\"email.invalid\">{{getErrorMessage()}}</mat-error>\r\n                </mat-form-field>\r\n              </div>\r\n              <div style=\"width: 80%\">\r\n                <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                  <mat-label>{{'Login.password' | translate}}</mat-label>\r\n                  <input id=\"regPassword\" matInput formControlName=\"password\" [(ngModel)]=\"regUser.password\" [type]=\"'password'\" placeholder=\"{{'Login.placeholder-pass-reg' | translate}}\">\r\n                  <mat-icon matSuffix>lock</mat-icon>\r\n                </mat-form-field>\r\n              </div>\r\n              <div style=\"width: 80%\">\r\n                <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                  <mat-label>{{'Login.password2' | translate}}</mat-label>\r\n                  <input id=\"regPasswordRepeat\" matInput formControlName=\"passwordRepeat\" [(ngModel)]=\"regUser.passwordRepeat\" [type]=\"'password'\" placeholder=\"{{'Login.placeholder-pass-reg-rep' | translate}}\">\r\n                  <mat-icon matSuffix>lock</mat-icon>\r\n                </mat-form-field>\r\n              </div>\r\n              <div style=\"width: 80%\">\r\n                  <mat-checkbox id=\"CheckBox\" matInput formControlName=\"checkBox\" [(ngModel)]=\"regUser.checkBox\"></mat-checkbox>\r\n                  <a mat-button (click)=\"ReadGDPR()\">{{'Login.gdpr' | translate}}</a>\r\n              </div>\r\n            </mat-card-content>\r\n\r\n            <mat-card-actions> <!--Button area for the Registration side-->\r\n              <div class=\"button-container\">\r\n                <div class=\"flex-row-right\">\r\n                  <button mat-raised-button type=\"button\" color=\"secondary\" (click)=\"changeMode()\">{{'Login.cancel' | translate}}</button>\r\n                </div>\r\n                <div class=\"flex-row-right\">\r\n                  <button mat-raised-button type=\"submit\" color=\"primary\"  [disabled]=\"!registerForm.valid\">{{'Login.confirm' | translate}}</button>\r\n                </div>\r\n              </div>\r\n            </mat-card-actions>\r\n          </form>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </mat-card>\r\n</div>\r\n\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Menu/menu.component.html":
/*!********************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Menu/menu.component.html ***!
  \********************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<mat-toolbar class=\"flex-row-between\" [ngClass]=\"{'mat-primary' : !deleteMode, 'mat-warn' : deleteMode}\" style=\"padding: 0\">\r\n  <button mat-button>\r\n    <div class=\"flex-row-left\">\r\n      <div class=\"flex-row-evenlyer\">\r\n        <div class=\"flex-column flex-center\">\r\n          <div class=\"Grouse-Avatar\"></div>\r\n        </div>\r\n        <p>Grouse</p>\r\n      </div>\r\n    </div>\r\n  </button>\r\n  <div class=\"flex-row-right\">\r\n    <div class=\"flex-row-between\">\r\n      <button mat-button class=\"enter-delete-mode\" (click)=\"deleteToggle()\">{{'Delete.Delete' | translate}}</button>\r\n      <div class=\"lang-test m-t-14\">\r\n        <button mat-button [matMenuTriggerFor]=\"langselect\">{{'Language.Language' | translate}}</button>\r\n        <mat-menu #langselect=\"matMenu\" xPosition=\"after\">\r\n          <button mat-menu-item (click)=\"changeLang('Bokmål')\">Bokmål</button>\r\n          <button mat-menu-item (click)=\"changeLang('Nynorsk')\">Nynorsk</button>\r\n          <button mat-menu-item (click)=\"changeLang('English')\">English</button>\r\n        </mat-menu>\r\n      </div>\r\n      <div *ngIf=\"userData._links['project-list-all'] === undefined\">\r\n        <button mat-button (click)=\"enterUserEdit()\">\r\n          <div class=\"flex-row-between\">\r\n            <div class=\"flex-center\">\r\n              <i class=\"material-icons flex-center navbar-icon icon-large\">account_box</i>\r\n            </div>\r\n            <p>{{this.userData.userName}}</p>\r\n          </div>\r\n        </button>\r\n      </div>\r\n      <div *ngIf=\"userData._links['project-list-all'] !== undefined\">\r\n        <button mat-button (click)=\"enterAdminPage()\">\r\n          <div class=\"flex-row-between\">\r\n            <p>{{'Admin.Admin' | translate}}</p>\r\n            <div class=\"flex-center\">\r\n              <i class=\"material-icons flex-center navbar-icon icon-large\">supervisor_account</i>\r\n            </div>\r\n          </div>\r\n        </button>\r\n        <button mat-button class=\"no-p\" (click)=\"enterUserEdit()\">\r\n            <p>{{this.userData.userName}}</p>\r\n        </button>\r\n      </div>\r\n      <button mat-button (click)=\"logout()\">\r\n        <div class=\"flex-column\">\r\n          <i class=\"material-icons flex-center navbar-icon\">exit_to_app</i>\r\n          <p class=\"no-m\">{{'Menu.Logout' | translate}}</p>\r\n        </div>\r\n      </button>\r\n    </div>\r\n  </div>\r\n</mat-toolbar>\r\n<div *ngIf=\"!deleteMode\" class=\"flex-row-left mal-container\">\r\n  <button mat-raised-button class=\"flex-column flex-center mal-card\" (click)=\"newProject()\">\r\n    <i class=\"material-icons\">add_circle_outline</i>\r\n    <p>{{'Menu.NewProject' | translate}}</p>\r\n  </button>\r\n  <button\r\n    mat-raised-button class=\"mal-card\"\r\n    *ngFor=\"let project of projects\"\r\n    (click)=\"openProject(project);\">\r\n    <div class=\"flex-column-squish\">\r\n      <p>{{project.ownedBy}}</p>\r\n      <h3>{{project.projectName}}</h3>\r\n      <h6>{{'Menu.Lastviewed' | translate}} {{project.lastModifiedDate.split('-')[2].split('T')[0]}}.{{project.lastModifiedDate.split('-')[1]}}.{{project.lastModifiedDate.split('-')[0]}}</h6>\r\n    </div>\r\n  </button>\r\n</div>\r\n<!--Delete mode-->\r\n<div *ngIf=\"deleteMode\" class=\"flex-row-left mal-container\">\r\n  <button\r\n    mat-raised-button class=\"mal-card\"\r\n    *ngFor=\"let project of getMyProjects(); let i = index\"\r\n    (click)=\"removeProject(project);\"\r\n    (mouseover)=\"shaking = i+1\"\r\n    (mouseleave)=\"shaking = 0\"\r\n    [@shake]=\"shaking === i+1 ? 'end' : ''\">\r\n    <div class=\"flex-column-squish\">\r\n      <p>{{project.ownedBy}}</p>\r\n      <h3>{{project.projectName}}</h3>\r\n      <h6>{{'Menu.Lastviewed' | translate}} {{project.lastModifiedDate.split('-')[2].split('T')[0]}}.{{project.lastModifiedDate.split('-')[1]}}.{{project.lastModifiedDate.split('-')[0]}}</h6>\r\n    </div>\r\n  </button>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/AdminDeleteUser/AdminDeleteUser.Dialog.html":
/*!**********************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/AdminDeleteUser/AdminDeleteUser.Dialog.html ***!
  \**********************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"flex-row-between no-p no-m\">\r\n  <h1 mat-dialog-title>{{'Admin.DeleteUser' | translate}}</h1>\r\n  <button mat-button class=\"no-p no-m h-80\" color=\"warn\" (click)=\"onNoClick()\">X</button>\r\n</div>\r\n<mat-dialog-content>\r\n  <p>{{'Admin.DeleteUserWarn' | translate}}</p>\r\n</mat-dialog-content>\r\n<mat-dialog-actions>\r\n  <div class=\"flex-row-between w-100\">\r\n    <button mat-raised-button (click)=\"onNoClick()\">{{'New.Cancel' | translate}}</button>\r\n    <button mat-raised-button [mat-dialog-close]=\"data\" color=\"warn\">{{'Admin.DeleteUser' | translate}}</button>\r\n  </div>\r\n</mat-dialog-actions>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/ConcurrencyResolver/ConcurrencyResolver.Dialog.html":
/*!******************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/ConcurrencyResolver/ConcurrencyResolver.Dialog.html ***!
  \******************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<h1 mat-dialog-title>{{\"Concurrency.Title\" | translate}}</h1>\r\n<div mat-dialog-content class=\"concurrency-main\">\r\n  <mat-radio-group [(ngModel)]=\"radioOption\">\r\n    <div class=\"concurrency-top-container\">\r\n      <div class=\"concurrency-choice-container\">\r\n        <div class=\"flex-row-left\">\r\n          <mat-label>{{\"Concurrency.YourVersionLabel\" | translate}}</mat-label>\r\n          <mat-radio-button value=\"client\" class=\"m-lr-10\"></mat-radio-button>\r\n        </div>\r\n        <mat-form-field *ngIf=\"type === 'requirementText'\" class=\"concurrency-textfield\" [formGroup]=\"formControl\">\r\n          <mat-label>{{\"Concurrency.YourVersion\" | translate}}</mat-label>\r\n          <textarea class=\"concurrency-textfield\" matInput placeholder=\"{{'Concurrency.TextFieldDesc' | translate}}\" id=\"NyttKrav\" [value]=\"clientVersion\" (input)=\"clientVersion = $event.target.value\"></textarea>\r\n        </mat-form-field>\r\n        <div class=\"flex-center w-100 h-100\">\r\n          <div *ngIf=\"type === 'priority'\" class=\"concurrency-textfield\" [formGroup]=\"formControl\">\r\n            <mat-button-toggle [checked]=\"clientVersion == 'O'\" (click)=\"updateRequirementPriority('client', 'O')\">O</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"clientVersion == '1'\" (click)=\"updateRequirementPriority('client', '1')\">1</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"clientVersion == '2'\" (click)=\"updateRequirementPriority('client', '2')\">2</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"clientVersion == 'O (i)'\" (click)=\"updateRequirementPriority('client', 'O (i)')\">O(i)</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"clientVersion == '1 (i)'\" (click)=\"updateRequirementPriority('client', '1 (i)')\">1(i)</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"clientVersion == '2 (i)'\" (click)=\"updateRequirementPriority('client', '2 (i)')\">2(i)</mat-button-toggle>\r\n          </div>\r\n        </div>\r\n      </div>\r\n\r\n      <div class=\"concurrency-choice-container\">\r\n        <div class=\"flex-row-left\">\r\n          <mat-label>{{\"Concurrency.ServerVersionLabel\" | translate}}</mat-label>\r\n          <mat-radio-button value=\"server\" class=\"m-lr-10\"></mat-radio-button>\r\n        </div>\r\n        <mat-form-field *ngIf=\"type === 'requirementText'\" class=\"concurrency-textfield\" [formGroup]=\"formControl\">\r\n          <mat-label>{{\"Concurrency.ServerVersion\" | translate}}</mat-label>\r\n          <textarea class=\"concurrency-textfield\"  matInput placeholder=\"{{'Concurrency.TextFieldDesc' | translate}}\" [value]=\"serverVersion\" (input)=\"serverVersion = $event.target.value\"></textarea>\r\n        </mat-form-field>\r\n        <div class=\"flex-center w-100 h-100\">\r\n          <div *ngIf=\"type === 'priority'\" class=\"concurrency-textfield\" [formGroup]=\"formControl\">\r\n            <mat-button-toggle [checked]=\"serverVersion == 'O'\" (click)=\"updateRequirementPriority('server', 'O')\">O</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"serverVersion == '1'\" (click)=\"updateRequirementPriority('server', '1')\">1</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"serverVersion == '2'\" (click)=\"updateRequirementPriority('server', '2')\">2</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"serverVersion == 'O (i)'\" (click)=\"updateRequirementPriority('server', 'O (i)')\">O(i)</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"serverVersion == '1 (i)'\" (click)=\"updateRequirementPriority('server', '1 (i)')\">1(i)</mat-button-toggle>\r\n            <mat-button-toggle [checked]=\"serverVersion == '2 (i)'\" (click)=\"updateRequirementPriority('server', '2 (i)')\">2(i)</mat-button-toggle>\r\n          </div>\r\n        </div>\r\n      </div>\r\n    </div>\r\n    <div class=\"flex-center p-20\">\r\n      <div class=\"concurrency-choice-container w-100 p-20\">\r\n        <div class=\"flex-row-left\" *ngIf=\"type === 'requirementText'\">\r\n          <mat-label>{{'Concurrency.NewVersionLabel' | translate}}</mat-label>\r\n          <mat-radio-button value=\"new\" class=\"m-lr-10\"></mat-radio-button>\r\n        </div>\r\n        <mat-form-field *ngIf=\"type === 'requirementText'\" class=\"concurrency-textfield\" [formGroup]=\"formControl\">\r\n          <mat-label>{{'Concurrency.NewVersion' | translate}}</mat-label>\r\n          <textarea class=\"concurrency-textfield\" matInput placeholder=\"{{'Concurrency.TextFieldDesc' | translate}}\" (input)=\"newVersion = $event.target.value\"></textarea>\r\n        </mat-form-field>\r\n      </div>\r\n    </div>\r\n  </mat-radio-group>\r\n</div>\r\n<div mat-dialog-actions class=\"flex-row-right\">\r\n  <button color=\"primary\" [disabled]=\"radioOption === ''\" mat-raised-button (click)=\"onNoClick()\">{{'Concurrency.Confirm' | translate}}</button>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/DeleteUser/DeleteUser.Dialog.html":
/*!************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/DeleteUser/DeleteUser.Dialog.html ***!
  \************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"flex-row-between no-p no-m\">\r\n  <h1 mat-dialog-title>{{'UserEdit.DeleteUserTitle' | translate}} {{this.userData.userName}}</h1>\r\n  <button mat-button class=\"no-p no-m h-80\" color=\"warn\" (click)=\"onNoClick()\" *ngIf=\"!loading && !deleted\">X</button>\r\n  <button mat-button class=\"no-p no-m h-80\" color=\"warn\" (click)=\"logout()\" *ngIf=\"!loading && deleted\">X</button>\r\n</div>\r\n<div mat-dialog-content class=\"w-100\">\r\n  <!--Information about deletion-->\r\n  <div *ngIf=\"!loading && !deleted && (error === null)\">\r\n    <h3>{{'UserEdit.DeleteUserUH1' | translate}}</h3>\r\n    <h4>{{'UserEdit.DeleteUserText1' | translate}}</h4>\r\n    <mat-divider></mat-divider>\r\n    <p>{{'UserEdit.DeleteUserText2' | translate}}</p>\r\n    <mat-divider></mat-divider>\r\n    <h3>{{'UserEdit.DeleteUserUH2' | translate}}</h3>\r\n    <p>{{'UserEdit.DeleteUserText3' | translate}}</p>\r\n    <mat-divider></mat-divider>\r\n    <mat-expansion-panel [expanded]=\"checked\" [hideToggle]=\"true\" [disabled]=\"true\" class=\"m-t-10 m-b-20 pass-panel\">\r\n      <mat-expansion-panel-header>\r\n        <div class=\"flex-row-left\">\r\n          <p class=\"m-lr-10 text-force-black\">{{'UserEdit.DeleteUserCheckDesc' | translate}}</p>\r\n          <mat-checkbox class=\"m-t-14 m-t-10\" [(ngModel)]=\"checked\"></mat-checkbox>\r\n        </div>\r\n      </mat-expansion-panel-header>\r\n      <div class=\"flex-row-between no-p no-m\">\r\n        <div class=\"w-70\">\r\n          <form [formGroup]=\"formGroup\">\r\n            <mat-form-field class=\"w-100\">\r\n              <mat-label>{{\"Login.password\" | translate}}</mat-label>\r\n              <input matInput formControlName=\"Pass\" [(ngModel)]=\"pass\" [type]=\"'password'\" (change)=\"checkPass()\" placeholder=\"{{'Login.placeholder-pass' | translate}}\">\r\n            </mat-form-field>\r\n          </form>\r\n        </div>\r\n        <div *ngIf=\"authorized === 1\" class=\"h-80\">\r\n          <mat-spinner diameter=\"50\" color=\"accent\"></mat-spinner>\r\n        </div>\r\n        <div *ngIf=\"authorized === -1\">\r\n          <mat-error>{{\"ErrorsAndWarns.Wrong-Pass\" | translate }}</mat-error>\r\n        </div>\r\n      </div>\r\n    </mat-expansion-panel>\r\n  </div>\r\n  <!--Loading Spinner-->\r\n  <div *ngIf=\"loading && authorized\" class=\"flex-center w-100\" style=\"height: 150px\">\r\n    <mat-spinner color=\"warn\"></mat-spinner>\r\n  </div>\r\n  <!--The user was deleted corectly-->\r\n  <div *ngIf=\"!loading && deleted\">\r\n    <div class=\"flex-center w-100\">\r\n      <div class=\"flex-column-squish\">\r\n        <div>\r\n          <h2>{{'UserEdit.DeleteConfirmedH' | translate}}</h2>\r\n          <p>{{'UserEdit.DeleteConfirmed' | translate}}</p>\r\n        </div>\r\n        <mat-divider></mat-divider>\r\n        <div class=\"m-t-14 flex-center\">\r\n          <button mat-raised-button color=\"warn\" (click)=\"onNoClick()\">{{'ErrorsAndWarns.Close' | translate}}</button>\r\n        </div>\r\n      </div>\r\n    </div>\r\n  </div>\r\n  <!--There was an error whilst deleting the user-->\r\n  <div *ngIf=\"!loading && !deleted && (error !== null)\">\r\n    <h3>{{'UserEdit.DeleteUserError' | translate}}</h3>\r\n    <p>{{'UserEdit.DeleteUserContact' | translate}}</p>\r\n    <div class=\"m-lr-10 color-lightgray\" style=\"font-family: 'Courier New'\">\r\n      <p>status: {{error.status}}</p>\r\n      <p>statusText: {{error.statusText}}</p>\r\n      <p>url: {{error.url}}</p>\r\n      <p>ok: {{error.ok}}</p>\r\n      <p>name: {{error.name}}</p>\r\n      <p>message: {{error.message}}</p>\r\n      <p>error:  {{error.error}}</p>\r\n    </div>\r\n  </div>\r\n</div>\r\n<div mat-dialog-actions>\r\n  <div class=\"flex-row-between w-100\" *ngIf=\"!loading && !deleted\">\r\n    <button mat-raised-button (click)=\"onNoClick()\">{{'New.Cancel' | translate}}</button>\r\n    <button mat-raised-button *ngIf=\"error === null\" [disabled]=\"authorized !== 2\" (click)=\"confirm()\">{{'UserEdit.ConfirmDeleteUserButton' | translate}}</button>\r\n  </div>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/NewProject/NewProject.Dialog.html":
/*!************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/NewProject/NewProject.Dialog.html ***!
  \************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<h1 mat-dialog-title>{{'New.Title' | translate}}</h1>\r\n<div mat-dialog-content>\r\n  <p>{{'New.Name' | translate}}</p>\r\n  <mat-form-field>\r\n    <input matInput [(ngModel)]=\"data.Name\">\r\n  </mat-form-field>\r\n  <mat-divider></mat-divider>\r\n  <div class=\"flex-column\">\r\n    <mat-form-field>\r\n      <mat-label>Velg Mal</mat-label>\r\n      <mat-select>\r\n        <mat-option *ngFor=\"let template of data.Templates\" [value]=\"template\" class=\"w-100\" (onSelectionChange)=\"data.SelectedTemplate = template\">\r\n          <p>{{template.templateName}} - Laget av {{template.ownedBy}}</p>\r\n        </mat-option>\r\n      </mat-select>\r\n    </mat-form-field>\r\n  </div>\r\n</div>\r\n<div mat-dialog-actions class=\"flex-row-between\">\r\n  <button mat-raised-button color=\"warn\" (click)=\"onNoClick()\">{{'New.Cancel' | translate}}</button>\r\n  <button mat-raised-button color=\"primary\" [mat-dialog-close]=\"data\">{{'New.Create' | translate}}</button>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/PasswordChangeConfirmed/PasswordChangeConfirmed.Dialog.html":
/*!**************************************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/PasswordChangeConfirmed/PasswordChangeConfirmed.Dialog.html ***!
  \**************************************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<h1 mat-dialog-title>{{'UserEdit.ConfirmedPasswordChangeDialogTitle' | translate}}</h1>\r\n<div mat-dialog-content>\r\n  <p>{{'UserEdit.ConfirmedPasswordChangeDialogText' | translate}}</p>\r\n</div>\r\n<div mat-dialog-actions class=\"flex-row-right\">\r\n  <button mat-raised-button color=\"primary\" (click)=\"onNoClick()\">{{'ErrorsAndWarns.Close' | translate}}</button>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/RemoveProject/RemoveProject.Dialog.html":
/*!******************************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/RemoveProject/RemoveProject.Dialog.html ***!
  \******************************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<h1 mat-dialog-title>{{'Delete.Confirm' | translate}}</h1>\r\n<div mat-dialog-content>\r\n  <p>{{'Delete.Sure?' | translate}}</p>\r\n</div>\r\n<div mat-dialog-actions class=\"flex-row-evenlyer\">\r\n  <button mat-raised-button (click)=\"onNoClick()\">{{'Delete.Cancel' | translate}}</button>\r\n  <button mat-raised-button color=\"warn\" [mat-dialog-close]=\"data\">{{'Delete.Delete' | translate}}</button>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/RemoveReq/RemoveReq.Dialog.html":
/*!**********************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/RemoveReq/RemoveReq.Dialog.html ***!
  \**********************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<h1 mat-dialog-title>{{'Remove.Confirm' | translate}}</h1>\r\n<div mat-dialog-content>\r\n  <p>{{'Remove.Sure?' | translate}}</p>\r\n</div>\r\n<div mat-dialog-actions class=\"flex-row-evenlyer\">\r\n  <button mat-raised-button (click)=\"onNoClick()\">{{'Remove.Cancel' | translate}}</button>\r\n  <button mat-raised-button color=\"warn\" [mat-dialog-close]=\"data\">{{'Remove.Delete' | translate}}</button>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/ShareMenu/ShareMenu.Dialog.html":
/*!**********************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/ShareMenu/ShareMenu.Dialog.html ***!
  \**********************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<div class=\"flex-row-between no-p no-m\">\r\n  <h1 mat-dialog-title>{{'KravEdit.Share' | translate}}</h1>\r\n  <button mat-button class=\"no-p no-m h-80\" color=\"warn\" (click)=\"onNoClick()\">X</button>\r\n</div>\r\n<div mat-dialog-content>\r\n  <!--Active Shares-->\r\n  <div class=\"w-100 share-menu-list-box\">\r\n    <mat-list>\r\n      <mat-list-item *ngFor=\"let share of shares\">\r\n        <div class=\"flex-row-between w-100\">\r\n          <p>{{share.username}}</p>\r\n          <button mat-raised-button color=\"warn\" class=\"h-80 m-t-10 w-120px\" *ngIf=\"share.username !== userData.userName\" (click)=\"revokeShare(share.username)\">{{\"KravEdit.RevokeShare\" | translate}}</button>\r\n          <div class=\"color-success you-marker\" *ngIf=\"share.username === userData.userName\">{{\"KravEdit.You\" | translate}}</div>\r\n        </div>\r\n        <mat-divider></mat-divider>\r\n      </mat-list-item>\r\n    </mat-list>\r\n  </div>\r\n\r\n  <!--New sharing-->\r\n  <mat-divider></mat-divider>\r\n  <form [formGroup]=\"formgroup\" (ngSubmit)=\"addShare()\">\r\n    <div class=\"flex-row-evenlyer p-10\">\r\n      <mat-form-field class=\"w-100\">\r\n        <mat-label>{{\"KravEdit.EmailLabel\" | translate}}</mat-label>\r\n        <input id=\"email\" matInput formControlName=\"email\" [(ngModel)]=\"newShare\" required placeholder=\"{{'Login.placeholder-email' |translate}}\">\r\n        <mat-icon matSuffix>mail</mat-icon>\r\n        <mat-error *ngIf=\"formgroup.invalid\">{{formgroup.errors}}</mat-error>\r\n      </mat-form-field>\r\n      <button mat-raised-button color=\"primary\" type=\"submit\" [disabled]=\"formgroup.invalid\" class=\"h-80  m-t-10\">{{\"KravEdit.Share\" | translate}}</button>\r\n    </div>\r\n  </form>\r\n</div>\r\n<div mat-dialog-actions class=\"flex-row-right\">\r\n  <button mat-raised-button (click)=\"onNoClick()\">{{'ErrorsAndWarns.Close' | translate}}</button>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/UserEdit/userEdit.component.html":
/*!****************************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/UserEdit/userEdit.component.html ***!
  \****************************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<mat-toolbar color=\"primary\" class=\"flex-row-between\">\r\n  <button class=\"no-p\" mat-button (click)=\"goToMainMenu()\">\r\n    <div class=\"flex-row-left no-m\">\r\n      <div class=\"flex-row-evenlyer no-m\">\r\n        <div class=\"flex-column flex-center\">\r\n          <div class=\"Grouse-Avatar\"></div>\r\n        </div>\r\n        <p>Grouse</p>\r\n      </div>\r\n    </div>\r\n  </button>\r\n  <button mat-button (click)=\"logout()\">\r\n    <div class=\"flex-column\">\r\n      <i class=\"material-icons flex-center navbar-icon\">exit_to_app</i>\r\n      <p class=\"no-m\">{{'UserEdit.Logout' | translate}}</p>\r\n    </div>\r\n  </button>\r\n</mat-toolbar>\r\n<div class=\"flex-column-start p-20\">\r\n  <div class=\"flex-row-between\">\r\n    <h3>{{'UserEdit.Usersettings' | translate}}</h3>\r\n    <p>{{this.userData.userName}}</p>\r\n  </div>\r\n  <mat-divider></mat-divider>\r\n  <div class=\"main-container\">\r\n\r\n    <!--User info-->\r\n    <mat-card class=\"cards no-p\">\r\n      <mat-card-header class=\"no-p flex-center\">\r\n        <h4>{{'UserEdit.Details' | translate}}</h4>\r\n      </mat-card-header>\r\n      <mat-card-content>\r\n        <div class=\"flex-row-between m-lr-10\">\r\n          <p class=\"bold\">{{'UserEdit.Email' | translate}}</p>\r\n          <p>{{this.userData.userName}}</p>\r\n        </div>\r\n        <mat-divider></mat-divider>\r\n        <div class=\"flex-row-between m-lr-10\">\r\n          <p class=\"bold\">{{'UserEdit.Created' | translate}}</p>\r\n          <p>Utilgjengelig</p>\r\n        </div>\r\n      </mat-card-content>\r\n      <div class=\"flex-column-end user-info-bottom\">\r\n        <div class=\"flex-row flex-center\">\r\n          <button mat-raised-button color=\"warn\" (click)=\"deleteUser()\">Slett Bruker</button>\r\n        </div>\r\n      </div>\r\n    </mat-card>\r\n\r\n    <!-- Password and e-mail options-->\r\n    <mat-card class=\"cards no-p\">\r\n      <mat-card-header class=\"no-p flex-center\">\r\n        <h4>{{'UserEdit.Administration' | translate}}</h4>\r\n      </mat-card-header>\r\n      <mat-card-content class=\"p-10\">\r\n        <!--Password-->\r\n        <mat-card class=\"m-t-10\">\r\n          <form [formGroup]=\"passwordForm\" (ngSubmit)=\"passwordForm && newPasswordSubmit()\" novalidate>\r\n            <mat-card-content>\r\n              <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                <mat-label>{{'UserEdit.Password-Old' | translate}}</mat-label>\r\n                <input matInput formControlName=\"oldP\" [(ngModel)]=\"oldPassword\" [type]=\"'password'\" placeholder=\"{{'UserEdit.Password-Old-Placeholder' | translate}}\">\r\n                <mat-icon matSuffix>lock</mat-icon>\r\n              </mat-form-field>\r\n              <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                <mat-label>{{'UserEdit.Password-New' | translate}}</mat-label>\r\n                <input matInput formControlName=\"newP\" #newPassord [(ngModel)]=\"newPassword\" [type]=\"'password'\" placeholder=\"{{'UserEdit.Password-Old-Placeholder' | translate}}\">\r\n                <mat-icon matSuffix>lock</mat-icon>\r\n              </mat-form-field>\r\n              <mat-form-field style=\"width: 100%\" appearance=\"legacy\">\r\n                <mat-label>{{'UserEdit.Password-New-rep' | translate}}</mat-label>\r\n                <input matInput formControlName=\"repP\" [(ngModel)]=\"repeatPassword\" [type]=\"'password'\" placeholder=\"{{'UserEdit.Password-Old-Placeholder' | translate}}\">\r\n                <mat-icon matSuffix>lock</mat-icon>\r\n              </mat-form-field>\r\n            </mat-card-content>\r\n            <mat-card-actions class=\"flex-row-right no-p\">\r\n              <button type=\"submit\" mat-raised-button color=\"primary\" [disabled]=\"passwordForm.invalid\" class=\"no-m\">{{'UserEdit.Password-Change' | translate}}</button>\r\n            </mat-card-actions>\r\n          </form>\r\n        </mat-card>\r\n      </mat-card-content>\r\n    </mat-card>\r\n  </div>\r\n</div>\r\n");

/***/ }),

/***/ "./node_modules/raw-loader/dist/cjs.js!./src/app/app.component.html":
/*!**************************************************************************!*\
  !*** ./node_modules/raw-loader/dist/cjs.js!./src/app/app.component.html ***!
  \**************************************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = ("<!DOCTYPE html>\r\n<html lang=\"en\">\r\n  <head>\r\n      <meta charset=\"UTF-8\">\r\n      <title>Title</title>\r\n  </head>\r\n  <body>\r\n    <div *ngIf=\"loading\" class=\"flex-column\">\r\n      <div class=\"flex-row\">\r\n        <mat-spinner></mat-spinner>\r\n      </div>\r\n    </div>\r\n    <router-outlet>\r\n    </router-outlet>\r\n  </body>\r\n</html>\r\n");

/***/ }),

/***/ "./node_modules/tslib/tslib.es6.js":
/*!*****************************************!*\
  !*** ./node_modules/tslib/tslib.es6.js ***!
  \*****************************************/
/*! exports provided: __extends, __assign, __rest, __decorate, __param, __metadata, __awaiter, __generator, __exportStar, __values, __read, __spread, __spreadArrays, __await, __asyncGenerator, __asyncDelegator, __asyncValues, __makeTemplateObject, __importStar, __importDefault */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__extends", function() { return __extends; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__assign", function() { return __assign; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__rest", function() { return __rest; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__decorate", function() { return __decorate; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__param", function() { return __param; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__metadata", function() { return __metadata; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__awaiter", function() { return __awaiter; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__generator", function() { return __generator; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__exportStar", function() { return __exportStar; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__values", function() { return __values; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__read", function() { return __read; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__spread", function() { return __spread; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__spreadArrays", function() { return __spreadArrays; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__await", function() { return __await; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__asyncGenerator", function() { return __asyncGenerator; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__asyncDelegator", function() { return __asyncDelegator; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__asyncValues", function() { return __asyncValues; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__makeTemplateObject", function() { return __makeTemplateObject; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__importStar", function() { return __importStar; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "__importDefault", function() { return __importDefault; });
/*! *****************************************************************************
Copyright (c) Microsoft Corporation. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at http://www.apache.org/licenses/LICENSE-2.0

THIS CODE IS PROVIDED ON AN *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY IMPLIED
WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A PARTICULAR PURPOSE,
MERCHANTABLITY OR NON-INFRINGEMENT.

See the Apache Version 2.0 License for specific language governing permissions
and limitations under the License.
***************************************************************************** */
/* global Reflect, Promise */

var extendStatics = function(d, b) {
    extendStatics = Object.setPrototypeOf ||
        ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
        function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
    return extendStatics(d, b);
};

function __extends(d, b) {
    extendStatics(d, b);
    function __() { this.constructor = d; }
    d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
}

var __assign = function() {
    __assign = Object.assign || function __assign(t) {
        for (var s, i = 1, n = arguments.length; i < n; i++) {
            s = arguments[i];
            for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p)) t[p] = s[p];
        }
        return t;
    }
    return __assign.apply(this, arguments);
}

function __rest(s, e) {
    var t = {};
    for (var p in s) if (Object.prototype.hasOwnProperty.call(s, p) && e.indexOf(p) < 0)
        t[p] = s[p];
    if (s != null && typeof Object.getOwnPropertySymbols === "function")
        for (var i = 0, p = Object.getOwnPropertySymbols(s); i < p.length; i++) {
            if (e.indexOf(p[i]) < 0 && Object.prototype.propertyIsEnumerable.call(s, p[i]))
                t[p[i]] = s[p[i]];
        }
    return t;
}

function __decorate(decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
}

function __param(paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
}

function __metadata(metadataKey, metadataValue) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(metadataKey, metadataValue);
}

function __awaiter(thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
}

function __generator(thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (_) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
}

function __exportStar(m, exports) {
    for (var p in m) if (!exports.hasOwnProperty(p)) exports[p] = m[p];
}

function __values(o) {
    var m = typeof Symbol === "function" && o[Symbol.iterator], i = 0;
    if (m) return m.call(o);
    return {
        next: function () {
            if (o && i >= o.length) o = void 0;
            return { value: o && o[i++], done: !o };
        }
    };
}

function __read(o, n) {
    var m = typeof Symbol === "function" && o[Symbol.iterator];
    if (!m) return o;
    var i = m.call(o), r, ar = [], e;
    try {
        while ((n === void 0 || n-- > 0) && !(r = i.next()).done) ar.push(r.value);
    }
    catch (error) { e = { error: error }; }
    finally {
        try {
            if (r && !r.done && (m = i["return"])) m.call(i);
        }
        finally { if (e) throw e.error; }
    }
    return ar;
}

function __spread() {
    for (var ar = [], i = 0; i < arguments.length; i++)
        ar = ar.concat(__read(arguments[i]));
    return ar;
}

function __spreadArrays() {
    for (var s = 0, i = 0, il = arguments.length; i < il; i++) s += arguments[i].length;
    for (var r = Array(s), k = 0, i = 0; i < il; i++)
        for (var a = arguments[i], j = 0, jl = a.length; j < jl; j++, k++)
            r[k] = a[j];
    return r;
};

function __await(v) {
    return this instanceof __await ? (this.v = v, this) : new __await(v);
}

function __asyncGenerator(thisArg, _arguments, generator) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var g = generator.apply(thisArg, _arguments || []), i, q = [];
    return i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i;
    function verb(n) { if (g[n]) i[n] = function (v) { return new Promise(function (a, b) { q.push([n, v, a, b]) > 1 || resume(n, v); }); }; }
    function resume(n, v) { try { step(g[n](v)); } catch (e) { settle(q[0][3], e); } }
    function step(r) { r.value instanceof __await ? Promise.resolve(r.value.v).then(fulfill, reject) : settle(q[0][2], r); }
    function fulfill(value) { resume("next", value); }
    function reject(value) { resume("throw", value); }
    function settle(f, v) { if (f(v), q.shift(), q.length) resume(q[0][0], q[0][1]); }
}

function __asyncDelegator(o) {
    var i, p;
    return i = {}, verb("next"), verb("throw", function (e) { throw e; }), verb("return"), i[Symbol.iterator] = function () { return this; }, i;
    function verb(n, f) { i[n] = o[n] ? function (v) { return (p = !p) ? { value: __await(o[n](v)), done: n === "return" } : f ? f(v) : v; } : f; }
}

function __asyncValues(o) {
    if (!Symbol.asyncIterator) throw new TypeError("Symbol.asyncIterator is not defined.");
    var m = o[Symbol.asyncIterator], i;
    return m ? m.call(o) : (o = typeof __values === "function" ? __values(o) : o[Symbol.iterator](), i = {}, verb("next"), verb("throw"), verb("return"), i[Symbol.asyncIterator] = function () { return this; }, i);
    function verb(n) { i[n] = o[n] && function (v) { return new Promise(function (resolve, reject) { v = o[n](v), settle(resolve, reject, v.done, v.value); }); }; }
    function settle(resolve, reject, d, v) { Promise.resolve(v).then(function(v) { resolve({ value: v, done: d }); }, reject); }
}

function __makeTemplateObject(cooked, raw) {
    if (Object.defineProperty) { Object.defineProperty(cooked, "raw", { value: raw }); } else { cooked.raw = raw; }
    return cooked;
};

function __importStar(mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (Object.hasOwnProperty.call(mod, k)) result[k] = mod[k];
    result.default = mod;
    return result;
}

function __importDefault(mod) {
    return (mod && mod.__esModule) ? mod : { default: mod };
}


/***/ }),

/***/ "./src/app/Admin/Admin.component.css":
/*!*******************************************!*\
  !*** ./src/app/Admin/Admin.component.css ***!
  \*******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".main-div{\r\n  width: 100%;\r\n  height: 100%;\r\n  margin-top: 10px;\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: center;\r\n          justify-content: center;\r\n  flex-wrap: wrap;\r\n}\r\n\r\n.secondary-div{\r\n  width: 500px;\r\n  height: 600px;\r\n  margin-right: 10px;\r\n  margin-left: 10px;\r\n}\r\n\r\n.search-bar{\r\n  height: 60px;\r\n}\r\n\r\n.list-div{\r\n  height: 400px;\r\n  overflow-y: scroll;\r\n  border-radius: 10px;\r\n  background-color: whitesmoke;\r\n  margin-top: 10px;\r\n}\r\n\r\n.details{\r\n  height: 470px;\r\n}\r\n\r\n.projects-and-users-minibox{\r\n  border-radius: 5px;\r\n  box-shadow: 1px 1px 2px gray;\r\n  height: 190px;\r\n  width: 95%;\r\n  background-color: whitesmoke;\r\n  overflow-y: scroll;\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  flex-wrap: wrap;\r\n  padding: 10px;\r\n  align-content: flex-start;\r\n}\r\n\r\n.box-button{\r\n  height: 50px;\r\n  margin: 5px;\r\n}\r\n\r\n.h-50{\r\n  height: 50px;\r\n}\r\n\r\n.project-box{\r\n  height: 220px;\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvQWRtaW4vQWRtaW4uY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLFdBQVc7RUFDWCxZQUFZO0VBQ1osZ0JBQWdCO0VBQ2hCLG9CQUFhO0VBQWIsYUFBYTtFQUNiLDhCQUFtQjtFQUFuQiw2QkFBbUI7VUFBbkIsbUJBQW1CO0VBQ25CLHdCQUF1QjtVQUF2Qix1QkFBdUI7RUFDdkIsZUFBZTtBQUNqQjs7QUFFQTtFQUNFLFlBQVk7RUFDWixhQUFhO0VBQ2Isa0JBQWtCO0VBQ2xCLGlCQUFpQjtBQUNuQjs7QUFFQTtFQUNFLFlBQVk7QUFDZDs7QUFFQTtFQUNFLGFBQWE7RUFDYixrQkFBa0I7RUFDbEIsbUJBQW1CO0VBQ25CLDRCQUE0QjtFQUM1QixnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxhQUFhO0FBQ2Y7O0FBRUE7RUFDRSxrQkFBa0I7RUFDbEIsNEJBQTRCO0VBQzVCLGFBQWE7RUFDYixVQUFVO0VBQ1YsNEJBQTRCO0VBQzVCLGtCQUFrQjtFQUNsQixvQkFBYTtFQUFiLGFBQWE7RUFDYiw4QkFBbUI7RUFBbkIsNkJBQW1CO1VBQW5CLG1CQUFtQjtFQUNuQixlQUFlO0VBQ2YsYUFBYTtFQUNiLHlCQUF5QjtBQUMzQjs7QUFFQTtFQUNFLFlBQVk7RUFDWixXQUFXO0FBQ2I7O0FBRUE7RUFDRSxZQUFZO0FBQ2Q7O0FBRUE7RUFDRSxhQUFhO0FBQ2YiLCJmaWxlIjoic3JjL2FwcC9BZG1pbi9BZG1pbi5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLm1haW4tZGl2e1xyXG4gIHdpZHRoOiAxMDAlO1xyXG4gIGhlaWdodDogMTAwJTtcclxuICBtYXJnaW4tdG9wOiAxMHB4O1xyXG4gIGRpc3BsYXk6IGZsZXg7XHJcbiAgZmxleC1kaXJlY3Rpb246IHJvdztcclxuICBqdXN0aWZ5LWNvbnRlbnQ6IGNlbnRlcjtcclxuICBmbGV4LXdyYXA6IHdyYXA7XHJcbn1cclxuXHJcbi5zZWNvbmRhcnktZGl2e1xyXG4gIHdpZHRoOiA1MDBweDtcclxuICBoZWlnaHQ6IDYwMHB4O1xyXG4gIG1hcmdpbi1yaWdodDogMTBweDtcclxuICBtYXJnaW4tbGVmdDogMTBweDtcclxufVxyXG5cclxuLnNlYXJjaC1iYXJ7XHJcbiAgaGVpZ2h0OiA2MHB4O1xyXG59XHJcblxyXG4ubGlzdC1kaXZ7XHJcbiAgaGVpZ2h0OiA0MDBweDtcclxuICBvdmVyZmxvdy15OiBzY3JvbGw7XHJcbiAgYm9yZGVyLXJhZGl1czogMTBweDtcclxuICBiYWNrZ3JvdW5kLWNvbG9yOiB3aGl0ZXNtb2tlO1xyXG4gIG1hcmdpbi10b3A6IDEwcHg7XHJcbn1cclxuXHJcbi5kZXRhaWxze1xyXG4gIGhlaWdodDogNDcwcHg7XHJcbn1cclxuXHJcbi5wcm9qZWN0cy1hbmQtdXNlcnMtbWluaWJveHtcclxuICBib3JkZXItcmFkaXVzOiA1cHg7XHJcbiAgYm94LXNoYWRvdzogMXB4IDFweCAycHggZ3JheTtcclxuICBoZWlnaHQ6IDE5MHB4O1xyXG4gIHdpZHRoOiA5NSU7XHJcbiAgYmFja2dyb3VuZC1jb2xvcjogd2hpdGVzbW9rZTtcclxuICBvdmVyZmxvdy15OiBzY3JvbGw7XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBmbGV4LWRpcmVjdGlvbjogcm93O1xyXG4gIGZsZXgtd3JhcDogd3JhcDtcclxuICBwYWRkaW5nOiAxMHB4O1xyXG4gIGFsaWduLWNvbnRlbnQ6IGZsZXgtc3RhcnQ7XHJcbn1cclxuXHJcbi5ib3gtYnV0dG9ue1xyXG4gIGhlaWdodDogNTBweDtcclxuICBtYXJnaW46IDVweDtcclxufVxyXG5cclxuLmgtNTB7XHJcbiAgaGVpZ2h0OiA1MHB4O1xyXG59XHJcblxyXG4ucHJvamVjdC1ib3h7XHJcbiAgaGVpZ2h0OiAyMjBweDtcclxufVxyXG4iXX0= */");

/***/ }),

/***/ "./src/app/Admin/Admin.component.ts":
/*!******************************************!*\
  !*** ./src/app/Admin/Admin.component.ts ***!
  \******************************************/
/*! exports provided: adminComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "adminComponent", function() { return adminComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm2015/ngx-translate-core.js");
/* harmony import */ var _Modals_AdminDeleteUser_AdminDeleteUser_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../Modals/AdminDeleteUser/AdminDeleteUser.component */ "./src/app/Modals/AdminDeleteUser/AdminDeleteUser.component.ts");
/* harmony import */ var _Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../Modals/RemoveProject/RemoveProject.compnent */ "./src/app/Modals/RemoveProject/RemoveProject.compnent.ts");








let adminComponent = class adminComponent {
    constructor(http, router, dialogBox, translate) {
        this.translate = translate;
        this.http = http;
        this.router = router;
        this.dialogBox = dialogBox;
        this.translate = translate;
        translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
        translate.setDefaultLang('Bokmål');
        this.searchProject = "";
        this.searchUser = "";
        this.listOfProjects = [];
        this.listOfUsers = [];
        this.usermode = true;
        this.getUserData();
    }
    /**
     * getUserData
     * Fetches data related tot the current user
     */
    getUserData() {
        this.userData = JSON.parse(localStorage.getItem('UserData'));
        this.translate.setDefaultLang(this.userData.defaultLang);
        this.getListOfUsers();
        this.getListOfProjects();
    }
    /**
     * getListOfUsers
     * Fetches list from server of all users
     */
    getListOfUsers() {
        this.http.get(this.userData._links["create-user"].href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            if (this.searchUser !== "") {
                // @ts-ignore
                this.listOfUsers = result._embedded.users.filter(user => user.username.includes(this.searchUser));
            }
            else {
                // @ts-ignore
                this.listOfUsers = result._embedded.users;
            }
        }, error => {
            console.error(error);
        });
    }
    /**
     * selectThisUserName
     * Selects a user with given username
     * @param inn The username of the user to switch to
     */
    selectThisUserName(inn) {
        this.selectThisUser(this.listOfUsers.find(user => user.username === inn));
    }
    /**
     * selectThisUser
     * Selects a given user
     * @param inn the user to select
     */
    selectThisUser(inn) {
        this.usermode = true;
        this.selectedUser = inn;
    }
    /**
     * selectThisProject
     * Selects a given project
     * @param inn the project to select
     */
    selectThisProject(inn) {
        this.usermode = false;
        this.selectedProject = inn;
        this.getUsersAssociatedWithProject(inn);
    }
    /**
     * getListOfProjects
     * Fetches a list of all projects from the server
     */
    getListOfProjects() {
        this.http.get(this.userData._links["project-list-all"].href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            if (this.searchProject !== "") {
                // @ts-ignore
                this.listOfProjects = result._embedded.projects.filter(project => project.projectName.includes(this.searchProject));
            }
            else {
                // @ts-ignore
                this.listOfProjects = result._embedded.projects;
            }
        }, error => {
            console.error(error);
        });
    }
    /**
     * filterProjectsByUser
     * Returns a list of projects filtered by the current selected user
     */
    filterProjectsByUser() {
        return this.listOfProjects.filter(project => project.ownedBy === this.selectedUser.username);
    }
    /**
     * getUsersAssociatedWithProject
     * Fetches users that are associated with the current project including shares
     * @param project wich project to find users of
     */
    getUsersAssociatedWithProject(project) {
        // Resets the the array of users so that a loading spinner can be displayed
        this.usersOfThisProject = null;
        this.http.get(project._links.access.href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            // @ts-ignore
            this.usersOfThisProject = result._embedded.users;
            // tslint:disable-next-line:no-shadowed-variable
        }, error => {
            console.error(error);
        });
    }
    /**
     * deleteProject
     * Opens a dialog pulled from the main menu (is pulled from menu.component.ts) as a deletion prompt for deleting a project
     * didnt need remaking
     *
     * @param inn Which project to delete
     */
    deleteProject() {
        const dialogRef = this.dialogBox.open(_Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_7__["DeleteProjectDialog"], {
            width: '300px'
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.http.delete(this.selectedProject._links.self.href, {
                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                        Authorization: 'Bearer ' + this.userData.oauthClientSecret
                    })
                }).subscribe(
                // tslint:disable-next-line:no-shadowed-variable
                result => {
                    this.getListOfProjects();
                }, error => console.error(error));
            }
        });
    }
    /**
     * revokeShare
     * Removes a sharing link for the specified user on the currently selected project
     *
     * @param user Which user to remove the share for
     */
    revokeShare(user) {
        this.http.delete(this.selectedProject._links.share.href.replace('user_email_address', user.username), {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            this.getUsersAssociatedWithProject(this.selectedProject);
        }, error => {
            console.error(error);
        });
    }
    /**
     * deleteUser
     * Deletes the currently selected user
     */
    deleteUser() {
        const dialogRef = this.dialogBox.open(_Modals_AdminDeleteUser_AdminDeleteUser_component__WEBPACK_IMPORTED_MODULE_6__["AdminDeleteUserDialog"], {
            width: '300px'
        });
        dialogRef.afterClosed().subscribe(result => {
            if (result) {
                this.http.delete(this.selectedUser._links.self.href, {
                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                        Authorization: 'Bearer ' + this.userData.oauthClientSecret
                    })
                }).subscribe(result => {
                    this.getListOfUsers();
                }, error => {
                    console.error(error);
                });
            }
        });
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
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
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
     * enterUserEdit()
     * Opens the user edit page
     */
    enterUserEdit() {
        this.userData.nav = 'userEdit';
        localStorage.setItem('UserData', JSON.stringify(this.userData));
        this.router.navigate(['/userEdit']);
    }
};
adminComponent.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"] },
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__["MatDialog"] },
    { type: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_5__["TranslateService"] }
];
adminComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-root',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./Admin.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Admin/Admin.component.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./Admin.component.css */ "./src/app/Admin/Admin.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../common.css */ "./src/app/common.css")).default]
    })
], adminComponent);



/***/ }),

/***/ "./src/app/KravEdit/kravEdit.component.css":
/*!*************************************************!*\
  !*** ./src/app/KravEdit/kravEdit.component.css ***!
  \*************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".test-card{\r\n  margin: 10px;\r\n}\r\n\r\n.main{\r\n  padding-top: 200px;\r\n}\r\n\r\n.back-button{\r\n  height: 100px;\r\n  width: auto;\r\n}\r\n\r\n.back-button-container{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  width: 100%;\r\n  top: 0;\r\n  position: fixed;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: reverse;\r\n          flex-direction: row-reverse;\r\n}\r\n\r\n.sidebar{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  height: 95%;\r\n  width: 100%;\r\n}\r\n\r\n.example-tree-invisible {\r\n  display: none;\r\n}\r\n\r\n.example-tree ul,\r\n.example-tree li {\r\n  margin-top: 0;\r\n  margin-bottom: 0;\r\n  list-style-type: none;\r\n}\r\n\r\n.sidenav{\r\n  width: 350px;\r\n}\r\n\r\n.sidenav-content{\r\n  width: 100%;\r\n}\r\n\r\n.Krav-card{\r\n  margin-top: 5px;\r\n  height: 38%;\r\n  overflow: scroll;\r\n  overflow-x: hidden;\r\n}\r\n\r\n.Krav-card-edit{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: column;\r\n  margin-top: 5px;\r\n  height: 55%;\r\n  padding-bottom: 0px;\r\n  padding-top: 0px;\r\n}\r\n\r\n.Krav-form{\r\n  height: 100%;\r\n  width: 100%;\r\n}\r\n\r\n.Krav-options{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: center;\r\n          justify-content: center;\r\n  height: 100%;\r\n}\r\n\r\n.Krav-input{\r\n  min-height: 175px;\r\n  height: 100%;\r\n  width: 100%;\r\n}\r\n\r\n.Krav-status{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  flex-wrap: nowrap;\r\n  width: 100%;\r\n  height: 55px;\r\n}\r\n\r\n.Krav-options-button{\r\n  height: 80%;\r\n}\r\n\r\n.edit-column{\r\n  width: 80%;\r\n  max-width: 800px;\r\n  margin-left: 10%;\r\n}\r\n\r\n.status-bar{\r\n  height: 100%;\r\n  width: 240px;\r\n}\r\n\r\n::ng-deep.mat-tab-label, ::ng-deep.mat-tab-label-active{\r\n  min-width: 40px !important;\r\n}\r\n\r\n::ng-deep.mat-tab-labels{\r\n  max-width: 100px;\r\n  margin: 0px;\r\n}\r\n\r\n.status-bar-button-container{\r\n  height: 500px !important;\r\n  width: 10px;\r\n}\r\n\r\n.color-success{\r\n  background-color: green;\r\n  color: white;\r\n}\r\n\r\n.big-button{\r\n  height: 60px;\r\n  width: 120px;\r\n}\r\n\r\n.status-page{\r\n  width: 80%;\r\n  margin-right: 10%;\r\n}\r\n\r\n.unfinished-box{\r\n  background-color: rgb(245, 245, 245);\r\n  width: 90%;\r\n  height: 300px;\r\n  margin-left: 5%;\r\n  margin-bottom: 20px;\r\n  border-radius: 10px;\r\n  box-shadow: 2px 2px 5px lightgrey;\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  flex-wrap: wrap;\r\n  overflow-y: scroll;\r\n  padding: 10px;\r\n}\r\n\r\n.unfinished-button{\r\n  height: 50px;\r\n  width: 80px;\r\n  margin: 2px;\r\n  padding: 0px;\r\n}\r\n\r\n::ng-deep.mat-card-header-text{\r\n  margin: 0 !important;\r\n}\r\n\r\n.share-menu-list-box{\r\n  height: 250px;\r\n  box-shadow: 2px 2px 5px lightgrey;\r\n  border-radius: 10px;\r\n  margin-bottom: 10px;\r\n  overflow-y: scroll;\r\n}\r\n\r\n.you-marker{\r\n  height: 30px;\r\n  width: 100px;\r\n  text-align: center;\r\n  padding-left: 10px;\r\n  padding-right: 10px;\r\n  padding-top: 5px;\r\n  margin-top: 10px;\r\n  border-radius: 5px;\r\n  box-shadow: 2px 2px 5px lightgrey;\r\n}\r\n\r\n.w-120px{\r\n  width: 120px;\r\n}\r\n\r\n.concurrency-choice-container{\r\n  width: 400px;\r\n  height: 200px;\r\n  margin: 15px;\r\n  padding: 10px;\r\n  box-shadow: 1px 1px 2px dimgray;\r\n  border-radius: 4px;\r\n}\r\n\r\n.concurrency-top-container {\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: center;\r\n          justify-content: center;\r\n  flex-wrap: wrap;\r\n}\r\n\r\n.concurrency-textfield {\r\n  width: 100%;\r\n  height: 140px;\r\n}\r\n\r\n.concurrency-main {\r\n  height: 600px;\r\n}\r\n\r\n.download-button-container{\r\n  width: 350px;\r\n  height: 50px;\r\n  padding-left: 10px;\r\n  padding-right: 10px;\r\n  padding-top: 10px;\r\n  box-shadow: 1px 1px 2px dimgray;\r\n  border-radius: 4px 0 0 4px;\r\n  margin-top: 10px;\r\n  background-color: whitesmoke;\r\n}\r\n\r\n.download-button{\r\n  height: 80px;\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvS3JhdkVkaXQva3JhdkVkaXQuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLFlBQVk7QUFDZDs7QUFFQTtFQUNFLGtCQUFrQjtBQUNwQjs7QUFFQTtFQUNFLGFBQWE7RUFDYixXQUFXO0FBQ2I7O0FBRUE7RUFDRSxvQkFBYTtFQUFiLGFBQWE7RUFDYixXQUFXO0VBQ1gsTUFBTTtFQUNOLGVBQWU7RUFDZiw4QkFBMkI7RUFBM0IsOEJBQTJCO1VBQTNCLDJCQUEyQjtBQUM3Qjs7QUFFQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLFdBQVc7RUFDWCxXQUFXO0FBQ2I7O0FBRUE7RUFDRSxhQUFhO0FBQ2Y7O0FBRUE7O0VBRUUsYUFBYTtFQUNiLGdCQUFnQjtFQUNoQixxQkFBcUI7QUFDdkI7O0FBRUE7RUFDRSxZQUFZO0FBQ2Q7O0FBQ0E7RUFDRSxXQUFXO0FBQ2I7O0FBRUE7RUFDRSxlQUFlO0VBQ2YsV0FBVztFQUNYLGdCQUFnQjtFQUNoQixrQkFBa0I7QUFDcEI7O0FBRUE7RUFDRSxvQkFBYTtFQUFiLGFBQWE7RUFDYiw0QkFBc0I7RUFBdEIsNkJBQXNCO1VBQXRCLHNCQUFzQjtFQUN0QixlQUFlO0VBQ2YsV0FBVztFQUNYLG1CQUFtQjtFQUNuQixnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxZQUFZO0VBQ1osV0FBVztBQUNiOztBQUVBO0VBQ0Usb0JBQWE7RUFBYixhQUFhO0VBQ2IsOEJBQW1CO0VBQW5CLDZCQUFtQjtVQUFuQixtQkFBbUI7RUFDbkIsd0JBQXVCO1VBQXZCLHVCQUF1QjtFQUN2QixZQUFZO0FBQ2Q7O0FBQ0E7RUFDRSxpQkFBaUI7RUFDakIsWUFBWTtFQUNaLFdBQVc7QUFDYjs7QUFFQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLGlCQUFpQjtFQUNqQixXQUFXO0VBQ1gsWUFBWTtBQUNkOztBQUVBO0VBQ0UsV0FBVztBQUNiOztBQUVBO0VBQ0UsVUFBVTtFQUNWLGdCQUFnQjtFQUNoQixnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxZQUFZO0VBQ1osWUFBWTtBQUNkOztBQUVBO0VBQ0UsMEJBQTBCO0FBQzVCOztBQUVBO0VBQ0UsZ0JBQWdCO0VBQ2hCLFdBQVc7QUFDYjs7QUFFQTtFQUNFLHdCQUF3QjtFQUN4QixXQUFXO0FBQ2I7O0FBRUE7RUFDRSx1QkFBdUI7RUFDdkIsWUFBWTtBQUNkOztBQUVBO0VBQ0UsWUFBWTtFQUNaLFlBQVk7QUFDZDs7QUFFQTtFQUNFLFVBQVU7RUFDVixpQkFBaUI7QUFDbkI7O0FBRUE7RUFDRSxvQ0FBb0M7RUFDcEMsVUFBVTtFQUNWLGFBQWE7RUFDYixlQUFlO0VBQ2YsbUJBQW1CO0VBQ25CLG1CQUFtQjtFQUNuQixpQ0FBaUM7RUFDakMsb0JBQWE7RUFBYixhQUFhO0VBQ2IsOEJBQW1CO0VBQW5CLDZCQUFtQjtVQUFuQixtQkFBbUI7RUFDbkIsZUFBZTtFQUNmLGtCQUFrQjtFQUNsQixhQUFhO0FBQ2Y7O0FBRUE7RUFDRSxZQUFZO0VBQ1osV0FBVztFQUNYLFdBQVc7RUFDWCxZQUFZO0FBQ2Q7O0FBRUE7RUFDRSxvQkFBb0I7QUFDdEI7O0FBRUE7RUFDRSxhQUFhO0VBQ2IsaUNBQWlDO0VBQ2pDLG1CQUFtQjtFQUNuQixtQkFBbUI7RUFDbkIsa0JBQWtCO0FBQ3BCOztBQUVBO0VBQ0UsWUFBWTtFQUNaLFlBQVk7RUFDWixrQkFBa0I7RUFDbEIsa0JBQWtCO0VBQ2xCLG1CQUFtQjtFQUNuQixnQkFBZ0I7RUFDaEIsZ0JBQWdCO0VBQ2hCLGtCQUFrQjtFQUNsQixpQ0FBaUM7QUFDbkM7O0FBRUE7RUFDRSxZQUFZO0FBQ2Q7O0FBRUE7RUFDRSxZQUFZO0VBQ1osYUFBYTtFQUNiLFlBQVk7RUFDWixhQUFhO0VBQ2IsK0JBQStCO0VBQy9CLGtCQUFrQjtBQUNwQjs7QUFFQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLDhCQUFtQjtFQUFuQiw2QkFBbUI7VUFBbkIsbUJBQW1CO0VBQ25CLHdCQUF1QjtVQUF2Qix1QkFBdUI7RUFDdkIsZUFBZTtBQUNqQjs7QUFFQTtFQUNFLFdBQVc7RUFDWCxhQUFhO0FBQ2Y7O0FBRUE7RUFDRSxhQUFhO0FBQ2Y7O0FBRUE7RUFDRSxZQUFZO0VBQ1osWUFBWTtFQUNaLGtCQUFrQjtFQUNsQixtQkFBbUI7RUFDbkIsaUJBQWlCO0VBQ2pCLCtCQUErQjtFQUMvQiwwQkFBMEI7RUFDMUIsZ0JBQWdCO0VBQ2hCLDRCQUE0QjtBQUM5Qjs7QUFFQTtFQUNFLFlBQVk7QUFDZCIsImZpbGUiOiJzcmMvYXBwL0tyYXZFZGl0L2tyYXZFZGl0LmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIudGVzdC1jYXJke1xyXG4gIG1hcmdpbjogMTBweDtcclxufVxyXG5cclxuLm1haW57XHJcbiAgcGFkZGluZy10b3A6IDIwMHB4O1xyXG59XHJcblxyXG4uYmFjay1idXR0b257XHJcbiAgaGVpZ2h0OiAxMDBweDtcclxuICB3aWR0aDogYXV0bztcclxufVxyXG5cclxuLmJhY2stYnV0dG9uLWNvbnRhaW5lcntcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIHdpZHRoOiAxMDAlO1xyXG4gIHRvcDogMDtcclxuICBwb3NpdGlvbjogZml4ZWQ7XHJcbiAgZmxleC1kaXJlY3Rpb246IHJvdy1yZXZlcnNlO1xyXG59XHJcblxyXG4uc2lkZWJhcntcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIGhlaWdodDogOTUlO1xyXG4gIHdpZHRoOiAxMDAlO1xyXG59XHJcblxyXG4uZXhhbXBsZS10cmVlLWludmlzaWJsZSB7XHJcbiAgZGlzcGxheTogbm9uZTtcclxufVxyXG5cclxuLmV4YW1wbGUtdHJlZSB1bCxcclxuLmV4YW1wbGUtdHJlZSBsaSB7XHJcbiAgbWFyZ2luLXRvcDogMDtcclxuICBtYXJnaW4tYm90dG9tOiAwO1xyXG4gIGxpc3Qtc3R5bGUtdHlwZTogbm9uZTtcclxufVxyXG5cclxuLnNpZGVuYXZ7XHJcbiAgd2lkdGg6IDM1MHB4O1xyXG59XHJcbi5zaWRlbmF2LWNvbnRlbnR7XHJcbiAgd2lkdGg6IDEwMCU7XHJcbn1cclxuXHJcbi5LcmF2LWNhcmR7XHJcbiAgbWFyZ2luLXRvcDogNXB4O1xyXG4gIGhlaWdodDogMzglO1xyXG4gIG92ZXJmbG93OiBzY3JvbGw7XHJcbiAgb3ZlcmZsb3cteDogaGlkZGVuO1xyXG59XHJcblxyXG4uS3Jhdi1jYXJkLWVkaXR7XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBmbGV4LWRpcmVjdGlvbjogY29sdW1uO1xyXG4gIG1hcmdpbi10b3A6IDVweDtcclxuICBoZWlnaHQ6IDU1JTtcclxuICBwYWRkaW5nLWJvdHRvbTogMHB4O1xyXG4gIHBhZGRpbmctdG9wOiAwcHg7XHJcbn1cclxuXHJcbi5LcmF2LWZvcm17XHJcbiAgaGVpZ2h0OiAxMDAlO1xyXG4gIHdpZHRoOiAxMDAlO1xyXG59XHJcblxyXG4uS3Jhdi1vcHRpb25ze1xyXG4gIGRpc3BsYXk6IGZsZXg7XHJcbiAgZmxleC1kaXJlY3Rpb246IHJvdztcclxuICBqdXN0aWZ5LWNvbnRlbnQ6IGNlbnRlcjtcclxuICBoZWlnaHQ6IDEwMCU7XHJcbn1cclxuLktyYXYtaW5wdXR7XHJcbiAgbWluLWhlaWdodDogMTc1cHg7XHJcbiAgaGVpZ2h0OiAxMDAlO1xyXG4gIHdpZHRoOiAxMDAlO1xyXG59XHJcblxyXG4uS3Jhdi1zdGF0dXN7XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBmbGV4LXdyYXA6IG5vd3JhcDtcclxuICB3aWR0aDogMTAwJTtcclxuICBoZWlnaHQ6IDU1cHg7XHJcbn1cclxuXHJcbi5LcmF2LW9wdGlvbnMtYnV0dG9ue1xyXG4gIGhlaWdodDogODAlO1xyXG59XHJcblxyXG4uZWRpdC1jb2x1bW57XHJcbiAgd2lkdGg6IDgwJTtcclxuICBtYXgtd2lkdGg6IDgwMHB4O1xyXG4gIG1hcmdpbi1sZWZ0OiAxMCU7XHJcbn1cclxuXHJcbi5zdGF0dXMtYmFye1xyXG4gIGhlaWdodDogMTAwJTtcclxuICB3aWR0aDogMjQwcHg7XHJcbn1cclxuXHJcbjo6bmctZGVlcC5tYXQtdGFiLWxhYmVsLCA6Om5nLWRlZXAubWF0LXRhYi1sYWJlbC1hY3RpdmV7XHJcbiAgbWluLXdpZHRoOiA0MHB4ICFpbXBvcnRhbnQ7XHJcbn1cclxuXHJcbjo6bmctZGVlcC5tYXQtdGFiLWxhYmVsc3tcclxuICBtYXgtd2lkdGg6IDEwMHB4O1xyXG4gIG1hcmdpbjogMHB4O1xyXG59XHJcblxyXG4uc3RhdHVzLWJhci1idXR0b24tY29udGFpbmVye1xyXG4gIGhlaWdodDogNTAwcHggIWltcG9ydGFudDtcclxuICB3aWR0aDogMTBweDtcclxufVxyXG5cclxuLmNvbG9yLXN1Y2Nlc3N7XHJcbiAgYmFja2dyb3VuZC1jb2xvcjogZ3JlZW47XHJcbiAgY29sb3I6IHdoaXRlO1xyXG59XHJcblxyXG4uYmlnLWJ1dHRvbntcclxuICBoZWlnaHQ6IDYwcHg7XHJcbiAgd2lkdGg6IDEyMHB4O1xyXG59XHJcblxyXG4uc3RhdHVzLXBhZ2V7XHJcbiAgd2lkdGg6IDgwJTtcclxuICBtYXJnaW4tcmlnaHQ6IDEwJTtcclxufVxyXG5cclxuLnVuZmluaXNoZWQtYm94e1xyXG4gIGJhY2tncm91bmQtY29sb3I6IHJnYigyNDUsIDI0NSwgMjQ1KTtcclxuICB3aWR0aDogOTAlO1xyXG4gIGhlaWdodDogMzAwcHg7XHJcbiAgbWFyZ2luLWxlZnQ6IDUlO1xyXG4gIG1hcmdpbi1ib3R0b206IDIwcHg7XHJcbiAgYm9yZGVyLXJhZGl1czogMTBweDtcclxuICBib3gtc2hhZG93OiAycHggMnB4IDVweCBsaWdodGdyZXk7XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBmbGV4LWRpcmVjdGlvbjogcm93O1xyXG4gIGZsZXgtd3JhcDogd3JhcDtcclxuICBvdmVyZmxvdy15OiBzY3JvbGw7XHJcbiAgcGFkZGluZzogMTBweDtcclxufVxyXG5cclxuLnVuZmluaXNoZWQtYnV0dG9ue1xyXG4gIGhlaWdodDogNTBweDtcclxuICB3aWR0aDogODBweDtcclxuICBtYXJnaW46IDJweDtcclxuICBwYWRkaW5nOiAwcHg7XHJcbn1cclxuXHJcbjo6bmctZGVlcC5tYXQtY2FyZC1oZWFkZXItdGV4dHtcclxuICBtYXJnaW46IDAgIWltcG9ydGFudDtcclxufVxyXG5cclxuLnNoYXJlLW1lbnUtbGlzdC1ib3h7XHJcbiAgaGVpZ2h0OiAyNTBweDtcclxuICBib3gtc2hhZG93OiAycHggMnB4IDVweCBsaWdodGdyZXk7XHJcbiAgYm9yZGVyLXJhZGl1czogMTBweDtcclxuICBtYXJnaW4tYm90dG9tOiAxMHB4O1xyXG4gIG92ZXJmbG93LXk6IHNjcm9sbDtcclxufVxyXG5cclxuLnlvdS1tYXJrZXJ7XHJcbiAgaGVpZ2h0OiAzMHB4O1xyXG4gIHdpZHRoOiAxMDBweDtcclxuICB0ZXh0LWFsaWduOiBjZW50ZXI7XHJcbiAgcGFkZGluZy1sZWZ0OiAxMHB4O1xyXG4gIHBhZGRpbmctcmlnaHQ6IDEwcHg7XHJcbiAgcGFkZGluZy10b3A6IDVweDtcclxuICBtYXJnaW4tdG9wOiAxMHB4O1xyXG4gIGJvcmRlci1yYWRpdXM6IDVweDtcclxuICBib3gtc2hhZG93OiAycHggMnB4IDVweCBsaWdodGdyZXk7XHJcbn1cclxuXHJcbi53LTEyMHB4e1xyXG4gIHdpZHRoOiAxMjBweDtcclxufVxyXG5cclxuLmNvbmN1cnJlbmN5LWNob2ljZS1jb250YWluZXJ7XHJcbiAgd2lkdGg6IDQwMHB4O1xyXG4gIGhlaWdodDogMjAwcHg7XHJcbiAgbWFyZ2luOiAxNXB4O1xyXG4gIHBhZGRpbmc6IDEwcHg7XHJcbiAgYm94LXNoYWRvdzogMXB4IDFweCAycHggZGltZ3JheTtcclxuICBib3JkZXItcmFkaXVzOiA0cHg7XHJcbn1cclxuXHJcbi5jb25jdXJyZW5jeS10b3AtY29udGFpbmVyIHtcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIGZsZXgtZGlyZWN0aW9uOiByb3c7XHJcbiAganVzdGlmeS1jb250ZW50OiBjZW50ZXI7XHJcbiAgZmxleC13cmFwOiB3cmFwO1xyXG59XHJcblxyXG4uY29uY3VycmVuY3ktdGV4dGZpZWxkIHtcclxuICB3aWR0aDogMTAwJTtcclxuICBoZWlnaHQ6IDE0MHB4O1xyXG59XHJcblxyXG4uY29uY3VycmVuY3ktbWFpbiB7XHJcbiAgaGVpZ2h0OiA2MDBweDtcclxufVxyXG5cclxuLmRvd25sb2FkLWJ1dHRvbi1jb250YWluZXJ7XHJcbiAgd2lkdGg6IDM1MHB4O1xyXG4gIGhlaWdodDogNTBweDtcclxuICBwYWRkaW5nLWxlZnQ6IDEwcHg7XHJcbiAgcGFkZGluZy1yaWdodDogMTBweDtcclxuICBwYWRkaW5nLXRvcDogMTBweDtcclxuICBib3gtc2hhZG93OiAxcHggMXB4IDJweCBkaW1ncmF5O1xyXG4gIGJvcmRlci1yYWRpdXM6IDRweCAwIDAgNHB4O1xyXG4gIG1hcmdpbi10b3A6IDEwcHg7XHJcbiAgYmFja2dyb3VuZC1jb2xvcjogd2hpdGVzbW9rZTtcclxufVxyXG5cclxuLmRvd25sb2FkLWJ1dHRvbntcclxuICBoZWlnaHQ6IDgwcHg7XHJcbn1cclxuIl19 */");

/***/ }),

/***/ "./src/app/KravEdit/kravEdit.component.ts":
/*!************************************************!*\
  !*** ./src/app/KravEdit/kravEdit.component.ts ***!
  \************************************************/
/*! exports provided: kravEditComponent, Requirment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "kravEditComponent", function() { return kravEditComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Requirment", function() { return Requirment; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _angular_cdk_tree__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/cdk/tree */ "./node_modules/@angular/cdk/esm2015/tree.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm2015/material.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");
/* harmony import */ var _models_statusPageData_model__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../models/statusPageData.model */ "./src/app/models/statusPageData.model.ts");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm2015/ngx-translate-core.js");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm2015/index.js");
/* harmony import */ var _Modals_ConcurrencyResolver_ConcurrencyResolver_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ../Modals/ConcurrencyResolver/ConcurrencyResolver.component */ "./src/app/Modals/ConcurrencyResolver/ConcurrencyResolver.component.ts");
/* harmony import */ var _Modals_RemoveReq_RemoveReq_component__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ../Modals/RemoveReq/RemoveReq.component */ "./src/app/Modals/RemoveReq/RemoveReq.component.ts");
/* harmony import */ var _Modals_ShareMenu_shareMenu_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ../Modals/ShareMenu/shareMenu.component */ "./src/app/Modals/ShareMenu/shareMenu.component.ts");













let kravEditComponent = 
// tslint:disable-next-line:class-name
class kravEditComponent {
    constructor(http, router, dialog, translate) {
        this.translate = translate;
        this.title = 'Grouse';
        this.hasChild = (_, node) => !!node.children && node.children.length > 0;
        this.loading = true;
        this.http = http;
        this.router = router;
        this.projectLink = '';
        this.sideBarOpen = false;
        this.dialog = dialog;
        this.statusbarData = [];
        this.statusPage = false;
        this.statpageData = new _models_statusPageData_model__WEBPACK_IMPORTED_MODULE_7__["statusPageData"]();
        translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
        translate.setDefaultLang('Bokmål');
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
        if (this.userData._links["project-list-all"] === undefined) {
            // The button should not be visible unless you have admin privileges
            console.warn('This user does not have admin privileges, you shouldt have been able to do this please contact an administrator!');
        }
        else {
            this.userData.nav = 'Admin';
            localStorage.setItem('UserData', JSON.stringify(this.userData));
            this.router.navigate(['/Admin']);
        }
    }
    ngOnInit() {
        this.userData = JSON.parse(localStorage.getItem('UserData'));
        this.translate.setDefaultLang(this.userData.defaultLang);
        this.projectLink = this.userData.currentProject._links.function.href;
        this.fetchMainData();
        this.treeControl = new _angular_cdk_tree__WEBPACK_IMPORTED_MODULE_4__["NestedTreeControl"](node => node.children);
        this.dataSource = new _angular_material__WEBPACK_IMPORTED_MODULE_5__["MatTreeNestedDataSource"]();
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
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            const newlyLoaded = (this.mainData === undefined);
            // @ts-ignore
            this.mainData = result._embedded.projectFunctionalities;
            // Calls all children, but makes sure that it is finnished before it proceeds
            let calls = 0;
            this.maxID = 0;
            const NavData = [];
            for (const prime of this.mainData) {
                const NavReqP = new Requirment();
                NavReqP.id = prime.projectFunctionalityId;
                NavReqP.name = prime.title;
                NavReqP.children = [];
                if (prime._links.function !== undefined) {
                    calls++;
                    this.http.get(prime._links.function.href, {
                        headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                            Authorization: 'Bearer ' + this.userData.oauthClientSecret,
                        }),
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
                                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                                        Authorization: 'Bearer ' + this.userData.oauthClientSecret
                                    })
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
                                                headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                                                    Authorization: 'Bearer ' + this.userData.oauthClientSecret
                                                })
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
                            }
                            else if (secondary._links.requirement !== undefined) {
                                calls++;
                                this.http.get(secondary._links.requirement.href, {
                                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                                        Authorization: 'Bearer ' + this.userData.oauthClientSecret
                                    })
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
                }
                else if (prime._links.requirement !== undefined) {
                    calls++;
                    this.http.get(prime._links.requirement.href, {
                        headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                            Authorization: 'Bearer ' + this.userData.oauthClientSecret
                        })
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
    getEtag(data) {
        let calls = 0;
        return new rxjs__WEBPACK_IMPORTED_MODULE_9__["Observable"](observer => {
            for (let element of data) {
                calls++;
                this.http.get(element._links.self.href, {
                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
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
                });
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
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                Authorization: 'Bearer' + this.userData.oauthClientSecret,
                Accept: this.statpageData.selectedFormat.type
            }),
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
    fixRequirmentsText(requirments) {
        const ret = [];
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
            const newreq = req;
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
    crunchGatheredData(newlyLoaded) {
        this.loading = false;
        this.sortMainData();
        // this.convertLegacyLinks();
        if (newlyLoaded) {
            const first = this.mainData[0];
            if (first.referenceProjectRequirement === undefined) {
                this.currentReq = first.referenceChildProjectFunctionality[0];
            }
            else {
                this.currentReq = first;
            }
            this.selectedTab = 0;
        }
        else {
            this.changeFunctionality(this.currentReq.projectFunctionalityId);
        }
        this.statusBarInfo();
        const patch = [{ op: "replace", path: "/priority", value: "O" }];
        this.getSupportedFormats();
    }
    /**
     * sortMainData
     * Sorts the main data in ascending order
     */
    sortMainData() {
        this.mainData.sort((a, b) => {
            return parseInt(a.functionalityNumber) - parseInt(b.functionalityNumber);
        });
        for (let primary of this.mainData) {
            if (primary.referenceChildProjectFunctionality !== undefined && primary.referenceChildProjectFunctionality.length > 0) {
                for (let secondary of primary.referenceChildProjectFunctionality) {
                    if (secondary.referenceChildProjectFunctionality !== undefined && secondary.referenceChildProjectFunctionality.length > 0) {
                        secondary.referenceChildProjectFunctionality.sort((a, b) => {
                            return parseInt(a.functionalityNumber) - parseInt(b.functionalityNumber);
                        });
                    }
                }
                primary.referenceChildProjectFunctionality.sort((a, b) => {
                    return parseInt(a.functionalityNumber) - parseInt(b.functionalityNumber);
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
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
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
    updateFunctionalityEtag(functionality) {
        const inMemory = this.findFunctionality(functionality.projectFunctionalityId);
        if (inMemory !== null) {
            if (inMemory.parent === undefined) {
                const index = this.mainData.indexOf(this.mainData.find(func => func.projectFunctionalityId === functionality.projectFunctionalityId));
                if (index !== -1) {
                    this.mainData[index].etag = functionality.etag;
                }
            }
            else if (inMemory.parent !== undefined) {
                if (inMemory.parent.parent !== undefined) {
                    const primeIndex = this.mainData.indexOf(this.mainData.find(func => func.projectFunctionalityId === inMemory.parent.parent.projectFunctionalityId));
                    const secIndex = this.mainData[primeIndex].referenceChildProjectFunctionality.indexOf(this.mainData[primeIndex].referenceChildProjectFunctionality.find(func => func.projectFunctionalityId === inMemory.parent.projectFunctionalityId));
                    const trdIndex = this.mainData[primeIndex].referenceChildProjectFunctionality[secIndex].referenceChildProjectFunctionality.indexOf(this.mainData[primeIndex].referenceChildProjectFunctionality[secIndex].referenceChildProjectFunctionality.find(func => func.projectFunctionalityId === inMemory.projectFunctionalityId));
                    this.mainData[primeIndex].referenceChildProjectFunctionality[secIndex].referenceChildProjectFunctionality[trdIndex].etag = functionality.etag;
                }
                else {
                    const primeIndex = this.mainData.indexOf(this.mainData.find(func => func.projectFunctionalityId === inMemory.parent.projectFunctionalityId));
                    const secIndex = this.mainData[primeIndex].referenceChildProjectFunctionality.indexOf(this.mainData[primeIndex].referenceChildProjectFunctionality.find(func => func.projectFunctionalityId === inMemory.projectFunctionalityId));
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
    changeFunctionality(id) {
        if (id === 0) { // Loads the statuspage
            this.statusPage = !this.statusPage;
            this.statPageLoad();
        }
        else {
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
    findFunctionality(id) {
        // Primary level
        let pfound = this.mainData.find(functionality => functionality.projectFunctionalityId === id);
        if (pfound === undefined) {
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
                        }
                        else if (secondary.projectFunctionalityId === id) {
                            return secondary;
                        }
                    }
                }
                else if (primary.projectFunctionalityId === id) {
                    return primary;
                }
            }
            return null;
        }
        else {
            return pfound;
        }
    }
    /**
     * updateRequirementText
     *
     * handles a change in requirement text.
     */
    updateRequirementText(index) {
        const req = this.currentReq.referenceProjectRequirement[index];
        // @ts-ignore
        req.requirementText = document.getElementById('field-' + index).value;
        const patch = [{ op: "replace", path: "/requirementText", value: req.requirementText }];
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
    updateFunctionalityProcessed(functionality) {
        functionality.processed = !functionality.processed;
        const patch = [{ op: "replace", path: "/processed", value: functionality.processed }];
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
    updateRequirementPriority(index, priority) {
        const patch = [{ op: "replace", path: "/priority", value: priority }];
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
    sendPatch(patch, url, etag) {
        return new rxjs__WEBPACK_IMPORTED_MODULE_9__["Observable"](observer => {
            this.http.patch(url, patch, {
                headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                    Authorization: 'Bearer ' + this.userData.oauthClientSecret,
                    ETAG: etag
                }),
                observe: "response"
            }).subscribe(result => {
                // @ts-ignore
                let returned = result.body;
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
                        }
                        else {
                            // "ignoring" processed
                            this.http.get(url, {
                                headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                                    Authorization: 'Bearer ' + this.userData.oauthClientSecret
                                }),
                                observe: "response"
                            }).subscribe(result => {
                                this.http.patch(url, patch, {
                                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                                        Authorization: 'Bearer ' + this.userData.oauthClientSecret,
                                        ETAG: result.headers.get('etag')
                                    }),
                                    observe: "response"
                                }).subscribe(result => {
                                    let returned = result.body;
                                    returned.etag = result.headers.get("etag");
                                    observer.next(returned);
                                    observer.complete();
                                }, error2 => {
                                    console.error(error2);
                                });
                            }, error1 => {
                                console.error(error1);
                            });
                        }
                    }
                    else {
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
        this.http.post(this.currentReq._links.requirement.href, {
            // @ts-ignore
            requirementText: textfield.value
        }, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
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
    removeRequirment(index) {
        const dialogref = this.dialog.open(_Modals_RemoveReq_RemoveReq_component__WEBPACK_IMPORTED_MODULE_11__["DeleteRequirmentDialog"], {
            width: '300px'
        });
        dialogref.afterClosed().subscribe(result => {
            if (result) {
                this.http.delete(this.currentReq.referenceProjectRequirement[index]._links.self.href, {
                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
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
    newReqPriorityChange(priority) {
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
        }
        else {
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
    findNextReq(id) {
        let switchto = null;
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
    findPreviousReq(id) {
        let switchto = null;
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
        const ret = [this.currentReq];
        for (let i = 0; i < 5; i++) {
            const add = this.findPreviousReq(ret[0].projectFunctionalityId);
            if (add === null) {
                break;
            }
            else {
                ret.unshift(add);
            }
        }
        while (ret.length < 11) {
            const add = this.findNextReq(ret[ret.length - 1].projectFunctionalityId);
            if (add !== null) {
                ret.push(add);
            }
            else if (ret.length > 9) {
                ret.unshift(this.findPreviousReq(ret[0].projectFunctionalityId));
                // tslint:disable-next-line:no-shadowed-variable
                const add = {
                    functionalityNumber: 'Ferdig',
                    projectFunctionalityId: 0,
                };
                // @ts-ignore
                ret.push(add);
            }
            else {
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
        this.getSupportedFormats().subscribe(result => { this.statpageData.sportedFormats = result; });
        let add = this.currentReq;
        while (add !== null) {
            if (!add.processed) {
                this.statpageData.unfinished.unshift(add);
            }
            add = this.findPreviousReq(add.projectFunctionalityId);
            // tslint:disable-next-line:max-line-length
            if (add !== null && this.statpageData.unfinished.length > 0 && this.statpageData.unfinished[0].projectFunctionalityId === add.projectFunctionalityId) {
                add = null;
            }
        }
        if (this.statpageData.unfinished.length === 0) {
            this.statpageData.finished = true;
        }
        else {
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
        }
        else {
            this.statpageData.progress = 100;
        }
        this.statpageData.loaded = true;
    }
    /**
     * getSupportedFormats
     * Fetches available formats from the server
     */
    getSupportedFormats() {
        return new rxjs__WEBPACK_IMPORTED_MODULE_9__["Observable"](observer => {
            this.http.get(this.userData.currentProject._links["supported-formats"].href, {
                headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpHeaders"]({
                    Authorization: 'Bearer ' + this.userData.oauthClientSecret
                })
            }).subscribe(result => {
                let formats = [];
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
        this.dialog.open(_Modals_ShareMenu_shareMenu_component__WEBPACK_IMPORTED_MODULE_12__["ShareMenu"], {
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
    resolveConcurrencyError(url, patch) {
        const data = {
            url: url,
            patch: patch,
            token: this.userData.oauthClientSecret
        };
        const dialogRef = this.dialog.open(_Modals_ConcurrencyResolver_ConcurrencyResolver_component__WEBPACK_IMPORTED_MODULE_10__["ConcurrencyResolver"], {
            data: data,
            width: '90%',
            minWidth: '500px',
            maxWidth: '1000px',
            height: '90%',
            minHeight: '750px',
            maxHeight: '100px'
        });
        return new rxjs__WEBPACK_IMPORTED_MODULE_9__["Observable"](observer => {
            dialogRef.afterClosed().subscribe(result => {
                observer.next(result);
                observer.complete();
            });
        });
    }
};
kravEditComponent.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_3__["HttpClient"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"] },
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_6__["MatDialog"] },
    { type: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_8__["TranslateService"] }
];
kravEditComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-root',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./kravEdit.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/KravEdit/kravEdit.component.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./kravEdit.component.css */ "./src/app/KravEdit/kravEdit.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../common.css */ "./src/app/common.css")).default]
    })
    // tslint:disable-next-line:class-name
], kravEditComponent);

// Needed for tree structure
class Requirment {
}
const TREE_DATA = [];


/***/ }),

/***/ "./src/app/Login/Login.component.css":
/*!*******************************************!*\
  !*** ./src/app/Login/Login.component.css ***!
  \*******************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".Login-page{\r\n  height: 100%;\r\n  width: 100%;\r\n}\r\n\r\n.login-div{\r\n  height: 450px;\r\n  max-width: 500px;\r\n  width: 90%;\r\n}\r\n\r\n.button-container{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  justify-content: space-around;\r\n}\r\n\r\n.Login-Background{\r\n  background-image: url('Login_Backround.jpg');\r\n  background-size: cover;\r\n  position: absolute;\r\n  opacity: 60%;\r\n  width: 100%;\r\n  height: 100%;\r\n}\r\n\r\n.divider{\r\n  height: 300px;\r\n  width: 5px;\r\n  background-color: gray;\r\n  opacity: 80%;\r\n}\r\n\r\n.login{\r\n  width: 100%;\r\n}\r\n\r\n.register{\r\n  width: 0%;\r\n}\r\n\r\n.navbar-icon{\r\n  height: 50px;\r\n  width: 50px;\r\n  font-size: 30px;\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvTG9naW4vTG9naW4uY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLFlBQVk7RUFDWixXQUFXO0FBQ2I7O0FBRUE7RUFDRSxhQUFhO0VBQ2IsZ0JBQWdCO0VBQ2hCLFVBQVU7QUFDWjs7QUFFQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLDZCQUE2QjtBQUMvQjs7QUFFQTtFQUNFLDRDQUF1RDtFQUN2RCxzQkFBc0I7RUFDdEIsa0JBQWtCO0VBQ2xCLFlBQVk7RUFDWixXQUFXO0VBQ1gsWUFBWTtBQUNkOztBQUVBO0VBQ0UsYUFBYTtFQUNiLFVBQVU7RUFDVixzQkFBc0I7RUFDdEIsWUFBWTtBQUNkOztBQUVBO0VBQ0UsV0FBVztBQUNiOztBQUVBO0VBQ0UsU0FBUztBQUNYOztBQUVBO0VBQ0UsWUFBWTtFQUNaLFdBQVc7RUFDWCxlQUFlO0FBQ2pCIiwiZmlsZSI6InNyYy9hcHAvTG9naW4vTG9naW4uY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5Mb2dpbi1wYWdle1xyXG4gIGhlaWdodDogMTAwJTtcclxuICB3aWR0aDogMTAwJTtcclxufVxyXG5cclxuLmxvZ2luLWRpdntcclxuICBoZWlnaHQ6IDQ1MHB4O1xyXG4gIG1heC13aWR0aDogNTAwcHg7XHJcbiAgd2lkdGg6IDkwJTtcclxufVxyXG5cclxuLmJ1dHRvbi1jb250YWluZXJ7XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBqdXN0aWZ5LWNvbnRlbnQ6IHNwYWNlLWFyb3VuZDtcclxufVxyXG5cclxuLkxvZ2luLUJhY2tncm91bmR7XHJcbiAgYmFja2dyb3VuZC1pbWFnZTogdXJsKFwic3JjL2Fzc2V0cy9Mb2dpbl9CYWNrcm91bmQuanBnXCIpO1xyXG4gIGJhY2tncm91bmQtc2l6ZTogY292ZXI7XHJcbiAgcG9zaXRpb246IGFic29sdXRlO1xyXG4gIG9wYWNpdHk6IDYwJTtcclxuICB3aWR0aDogMTAwJTtcclxuICBoZWlnaHQ6IDEwMCU7XHJcbn1cclxuXHJcbi5kaXZpZGVye1xyXG4gIGhlaWdodDogMzAwcHg7XHJcbiAgd2lkdGg6IDVweDtcclxuICBiYWNrZ3JvdW5kLWNvbG9yOiBncmF5O1xyXG4gIG9wYWNpdHk6IDgwJTtcclxufVxyXG5cclxuLmxvZ2lue1xyXG4gIHdpZHRoOiAxMDAlO1xyXG59XHJcblxyXG4ucmVnaXN0ZXJ7XHJcbiAgd2lkdGg6IDAlO1xyXG59XHJcblxyXG4ubmF2YmFyLWljb257XHJcbiAgaGVpZ2h0OiA1MHB4O1xyXG4gIHdpZHRoOiA1MHB4O1xyXG4gIGZvbnQtc2l6ZTogMzBweDtcclxufVxyXG4iXX0= */");

/***/ }),

/***/ "./src/app/Login/Login.component.ts":
/*!******************************************!*\
  !*** ./src/app/Login/Login.component.ts ***!
  \******************************************/
/*! exports provided: LoginComponent, GDPRContent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginComponent", function() { return LoginComponent; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "GDPRContent", function() { return GDPRContent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _app_animations__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../app.animations */ "./src/app/app.animations.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _models_register_model__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../models/register.model */ "./src/app/models/register.model.ts");
/* harmony import */ var _models_login_model__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ../models/login.model */ "./src/app/models/login.model.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _models_UserData_model__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../models/UserData.model */ "./src/app/models/UserData.model.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm2015/material.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm2015/ngx-translate-core.js");
/* harmony import */ var _common__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ../common */ "./src/app/common.ts");













let LoginComponent = class LoginComponent {
    constructor(http, formBuilder, router, snackBar, dialog, translate) {
        this.formBuilder = formBuilder;
        this.dialog = dialog;
        this.translate = translate;
        this.title = 'Grouse';
        this.regUser = new _models_register_model__WEBPACK_IMPORTED_MODULE_5__["RegisterModel"]();
        this.loginUser = new _models_login_model__WEBPACK_IMPORTED_MODULE_6__["LoginModel"]();
        // Email validator
        this.email = new _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormControl"]('', [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].email]);
        this.login = true;
        this.http = http;
        this.router = router;
        this.userData = new _models_UserData_model__WEBPACK_IMPORTED_MODULE_8__["UserData"]();
        this.shake = false;
        this.snackBar = snackBar;
        translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
        translate.setDefaultLang('Bokmål');
    }
    /**
     * getErrorMessage
     * Fetches error message for the form to display if not valid
     */
    getErrorMessage() {
        // @ts-ignore
        // tslint:disable-next-line:max-line-length
        return this.email.hasError('required') ? this.translate.get('ErrorsAndWarns.MustEnterEmail').value : this.email.hasError('email') ? this.translate.get('ErrorsAndWarns.InnvalidEmail').value : '';
    }
    /**
     * ngOnInit
     * Runs inital code for the page when it loads, builds form validators and fetches userData from local storage
     */
    ngOnInit() {
        this.registerForm = this.formBuilder.group({
            email: [this.regUser.email, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].email
                ]],
            password: [this.regUser.password, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].minLength(4),
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].maxLength(30)
                ]],
            passwordRepeat: [this.regUser.passwordRepeat, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].minLength(4),
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].maxLength(30)
                ]],
            checkBox: [this.regUser.checkBox, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].requiredTrue
                ]]
        });
        this.loginForm = this.formBuilder.group({
            email: [this.loginUser.email, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].email
                ]],
            password: [this.loginUser.password, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_3__["Validators"].required
                ]]
        });
        this.userData = JSON.parse(localStorage.getItem('UserData'));
    }
    /**
     * ReadGDPR
     * Opens the dialogbox containing the GDPR statment
     */
    ReadGDPR() {
        this.dialog.open(GDPRContent);
    }
    /**
     * registerSubmitt
     * Sends a request to the server requesting the creation of a new user with information from this class that is edited by the user
     */
    registerSubmit() {
        this.shake = false;
        if (this.regUser.password === this.regUser.passwordRepeat) { // Checks that passwords match
            // If everything is in order, sends the data to server
            const body = {
                username: this.regUser.email,
                password: this.regUser.password
            };
            this.http.post(this.userData._links['create-user'].href, body).subscribe(result => {
                // The transmission was a succsess and the server accepted the new user
                this.loginUser.password = this.regUser.password;
                this.loginUser.email = this.regUser.email;
                this.loginSubmit();
            }, error => {
                // There was an error
                // A user with the email entered allready exists
                if (error.error.message.startsWith('No GrouseUser exists')) {
                    this.shake = true;
                    // @ts-ignore
                    // tslint:disable-next-line:max-line-length
                    this.snackBar.open(this.translate.get('ErrorsAndWarns.UserAlreadyExists').value, this.translate.get('ErrorsAndWarns.Close').value);
                }
            });
        }
    }
    /**
     * loginSubmit
     * Sends a request for an auth token with supplied information from the user
     */
    loginSubmit() {
        // Resolves an error where refreshed user might have gotten an error due to unwanted data retention
        if (this.userData.oauthClientSecret !== 'secret') {
            this.userData.oauthClientSecret = 'secret';
            localStorage.setItem('UserData', JSON.stringify(this.userData));
        }
        this.shake = false; // Resets the shake animation
        // Sends login info to the server
        // Constructs the parameters that will be sendt to the server
        let body = new _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpParams"]();
        body = body.set('grant_type', 'password');
        body = body.append('username', this.loginUser.email.toString());
        body = body.append('password', this.loginUser.password.toString());
        body = body.append('client_id', this.userData.oauthClientId);
        this.http.post(this.userData._links['login OAuth2'].href, body, {
            // Constructs the headers
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpHeaders"]({
                Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + this.userData.oauthClientSecret),
                'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
            })
        }).subscribe(result => {
            // @ts-ignore
            this.userData.oauthClientSecret = result.access_token;
            this.getFurtherApiDetails();
        }, error => {
            if (error.error.error_description === 'Bad credentials') {
                this.shake = true; // Shakes the main card to illustrate that there was en error
                // @ts-ignore
                this.snackBar.open(this.translate.get('ErrorsAndWarns.Wrong-Password').value, this.translate.get('ErrorsAndWarns.Close').value);
            }
            else {
                // @ts-ignore
                alert(this.translate.get('ErrorsAndWarns.Unexpected').value);
                console.error(error);
            }
        });
    }
    /**
     * getFurtherApiDetails
     * Fetches API details requried after the user has signed in this is then used in the menu.component
     */
    getFurtherApiDetails() {
        this.http.get(_common__WEBPACK_IMPORTED_MODULE_12__["startUrl"], {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            this.userData.userName = this.loginUser.email;
            this.userData._links = result._links;
            this.userData._links['login OAuth2'].href = this.userData._links['login OAuth2'].href.split('?')[0];
            this.userData.nav = 'Menu';
            localStorage.setItem('UserData', JSON.stringify(this.userData));
            this.router.navigate(['/Menu']);
        }, error => {
            console.error(error);
        });
    }
    /**
     * changeMode
     * Swithces between
     */
    changeMode() {
        this.login = !this.login;
    }
    /**
     * changeLang
     * Changes the current language to the inputed via the lang parameter
     * @param lang The language to change into
     */
    changeLang(lang) {
        this.translate.use(lang);
        this.userData.defaultLang = lang;
        localStorage.setItem('UserData', JSON.stringify(this.userData));
    }
};
LoginComponent.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpClient"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_7__["Router"] },
    { type: _angular_material__WEBPACK_IMPORTED_MODULE_9__["MatSnackBar"] },
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_10__["MatDialog"] },
    { type: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_11__["TranslateService"] }
];
tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])()
], LoginComponent.prototype, "Checked", void 0);
LoginComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-root',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./Login.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Login/Login.component.html")).default,
        animations: [
            _app_animations__WEBPACK_IMPORTED_MODULE_2__["Animations"].toggleSlide,
            _app_animations__WEBPACK_IMPORTED_MODULE_2__["Animations"].shake
        ],
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./Login.component.css */ "./src/app/Login/Login.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../common.css */ "./src/app/common.css")).default]
    })
], LoginComponent);

let GDPRContent = class GDPRContent {
    constructor(dialogRef) {
        this.dialogRef = dialogRef;
    }
    /**
     * Is called once the dialog closes in this instance it will just close the dialog
     */
    OnNoClick() {
        this.dialogRef.close();
    }
};
GDPRContent.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_10__["MatDialogRef"] }
];
GDPRContent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'GDPR.Content',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./GDPRContent.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Login/GDPRContent.html")).default,
    })
], GDPRContent);



/***/ }),

/***/ "./src/app/Menu/menu.component.css":
/*!*****************************************!*\
  !*** ./src/app/Menu/menu.component.css ***!
  \*****************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".delete-button{\r\n  height: 70px;\r\n  width: 100px;\r\n  position: fixed;\r\n  bottom: 0px;\r\n}\r\n\r\n.mal-button{\r\n  height: 100px;\r\n  width: 175px;\r\n}\r\n\r\n.enter-delete-mode{\r\n  border-radius: 0px;\r\n  background-color: rgb(230, 0, 0);\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvTWVudS9tZW51LmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSxZQUFZO0VBQ1osWUFBWTtFQUNaLGVBQWU7RUFDZixXQUFXO0FBQ2I7O0FBRUE7RUFDRSxhQUFhO0VBQ2IsWUFBWTtBQUNkOztBQUVBO0VBQ0Usa0JBQWtCO0VBQ2xCLGdDQUFnQztBQUNsQyIsImZpbGUiOiJzcmMvYXBwL01lbnUvbWVudS5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLmRlbGV0ZS1idXR0b257XHJcbiAgaGVpZ2h0OiA3MHB4O1xyXG4gIHdpZHRoOiAxMDBweDtcclxuICBwb3NpdGlvbjogZml4ZWQ7XHJcbiAgYm90dG9tOiAwcHg7XHJcbn1cclxuXHJcbi5tYWwtYnV0dG9ue1xyXG4gIGhlaWdodDogMTAwcHg7XHJcbiAgd2lkdGg6IDE3NXB4O1xyXG59XHJcblxyXG4uZW50ZXItZGVsZXRlLW1vZGV7XHJcbiAgYm9yZGVyLXJhZGl1czogMHB4O1xyXG4gIGJhY2tncm91bmQtY29sb3I6IHJnYigyMzAsIDAsIDApO1xyXG59XHJcbiJdfQ== */");

/***/ }),

/***/ "./src/app/Menu/menu.component.ts":
/*!****************************************!*\
  !*** ./src/app/Menu/menu.component.ts ***!
  \****************************************/
/*! exports provided: MenuComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MenuComponent", function() { return MenuComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _models_UserData_model__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../models/UserData.model */ "./src/app/models/UserData.model.ts");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm2015/ngx-translate-core.js");
/* harmony import */ var _app_animations__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ../app.animations */ "./src/app/app.animations.ts");
/* harmony import */ var _Modals_NewProject_NewProject_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../Modals/NewProject/NewProject.component */ "./src/app/Modals/NewProject/NewProject.component.ts");
/* harmony import */ var _Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ../Modals/RemoveProject/RemoveProject.compnent */ "./src/app/Modals/RemoveProject/RemoveProject.compnent.ts");










let MenuComponent = class MenuComponent {
    constructor(http, router, dialogBox, translate) {
        this.translate = translate;
        this.title = 'Grouse';
        this.http = http;
        this.router = router;
        this.userData = new _models_UserData_model__WEBPACK_IMPORTED_MODULE_4__["UserData"]();
        this.dialogBox = dialogBox;
        translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
        translate.setDefaultLang('Bokmål');
        this.shaking = 0;
        this.deleteMode = false;
    }
    ngOnInit() {
        this.getUserData();
    }
    /**
     * getUserData
     * Fetches data related tot the current user
     */
    getUserData() {
        this.userData = JSON.parse(localStorage.getItem('UserData'));
        this.translate.setDefaultLang(this.userData.defaultLang);
        this.getActiveProjects();
    }
    /**
     * logout
     * Ends the current session and sends the user back to the loginpage
     */
    logout() {
        localStorage.clear();
        this.http.get(this.userData._links['logout OAuth2'].href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
        }, error => {
            this.router.navigate(['/']);
            location.reload();
            console.error(error);
        });
        this.router.navigate(['/']);
        location.reload();
    }
    /**
     * getActiveProjects
     * fetches active projects for the current user, includes projects that are shared with the current user
     */
    getActiveProjects() {
        this.http.get(this.userData._links['project-list'].href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            // @ts-ignore
            if (result._embedded !== undefined) {
                // @ts-ignore
                this.projects = result._embedded.projects;
            }
        }, error => {
            this.logout();
        });
    }
    /**
     * newProject
     * Creates a new project, opens a modal where the info is entered
     */
    newProject() {
        this.http.get(this.userData._links['template-list'].href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            // Opens new projectdialog
            const dialogRef = this.dialogBox.open(_Modals_NewProject_NewProject_component__WEBPACK_IMPORTED_MODULE_8__["NewProjectDialog"], {
                width: '450px',
                // @ts-ignore
                data: { Name: '', Templates: result._embedded.templates }
            });
            // After the dialog closers creates a new project
            // tslint:disable-next-line:no-shadowed-variable
            dialogRef.afterClosed().subscribe(result => {
                this.http.post(result.SelectedTemplate._links.project.href, { projectName: result.Name }, {
                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                        Authorization: 'Bearer ' + this.userData.oauthClientSecret
                    })
                    // tslint:disable-next-line:no-shadowed-variable
                }).subscribe(result => {
                    this.getActiveProjects();
                }, error => {
                    console.error(error);
                });
            });
        }, error => {
            console.error(error);
        });
    }
    /**
     * openProject
     * Send the user to the KravEdit page using a specified project
     *
     * @param project
     * The project to send the user to edit
     */
    openProject(project) {
        this.userData.currentProject = project;
        this.userData.nav = 'KravEdit';
        localStorage.setItem('UserData', JSON.stringify(this.userData));
        this.router.navigate(['/KravEdit']);
    }
    /**
     * removeProject
     * Delets a project
     *
     * @param project
     * The project to delete
     */
    removeProject(project) {
        const dialogref = this.dialogBox.open(_Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_9__["DeleteProjectDialog"], {
            width: '300px'
        });
        dialogref.afterClosed().subscribe(result => {
            if (result) {
                this.http.delete(project._links.self.href, {
                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                        Authorization: 'Bearer ' + this.userData.oauthClientSecret
                    })
                }).subscribe(
                // tslint:disable-next-line:no-shadowed-variable
                result => {
                    this.getActiveProjects();
                }, error => console.error(error));
            }
        });
    }
    /**
     * deleteToggle
     * Toggles the delete mode
     */
    deleteToggle() {
        this.deleteMode = !this.deleteMode;
    }
    /**
     * enterUserEdit
     *
     * Changes the current page tot eh user edit-page
     */
    enterUserEdit() {
        this.userData.nav = 'userEdit';
        localStorage.setItem('UserData', JSON.stringify(this.userData));
        this.router.navigate(['/userEdit']);
    }
    /**
     * getMyProjects
     *
     * Returns an arry with projects only owned by you
     */
    getMyProjects() {
        return this.projects.filter(project => project.ownedBy === this.userData.userName);
    }
    /**
     * changeLang
     * Changes the current language to the inputed via the lang parameter
     * @param lang The language to change into
     */
    changeLang(lang) {
        this.translate.use(lang);
        this.userData.defaultLang = lang;
        localStorage.setItem('UserData', JSON.stringify(this.userData));
    }
    /**
     * enterAdminPage
     * Opens the administration page, only available to admins
     */
    enterAdminPage() {
        if (this.userData._links["project-list-all"] === undefined) {
            // The button should not be visible unless you have admin privileges
            console.warn('This user does not have admin privileges, you shouldt have been able to do this please contact an administrator!');
        }
        else {
            this.userData.nav = 'Admin';
            localStorage.setItem('UserData', JSON.stringify(this.userData));
            this.router.navigate(['/Admin']);
        }
    }
};
MenuComponent.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"] },
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__["MatDialog"] },
    { type: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_6__["TranslateService"] }
];
MenuComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-root',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./menu.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Menu/menu.component.html")).default,
        animations: [
            _app_animations__WEBPACK_IMPORTED_MODULE_7__["Animations"].shake
        ],
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./menu.component.css */ "./src/app/Menu/menu.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../common.css */ "./src/app/common.css")).default]
    })
], MenuComponent);



/***/ }),

/***/ "./src/app/Modals/AdminDeleteUser/AdminDeleteUser.component.ts":
/*!*********************************************************************!*\
  !*** ./src/app/Modals/AdminDeleteUser/AdminDeleteUser.component.ts ***!
  \*********************************************************************/
/*! exports provided: AdminDeleteUserDialog */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AdminDeleteUserDialog", function() { return AdminDeleteUserDialog; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");



let AdminDeleteUserDialog = class AdminDeleteUserDialog {
    constructor(dialogRef, data) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.data = true;
    }
    onNoClick() {
        this.dialogRef.close();
    }
};
AdminDeleteUserDialog.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MatDialogRef"] },
    { type: Boolean, decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"],] }] }
];
AdminDeleteUserDialog = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'AdminDeleteUser.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./AdminDeleteUser.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/AdminDeleteUser/AdminDeleteUser.Dialog.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../Admin/Admin.component.css */ "./src/app/Admin/Admin.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../common.css */ "./src/app/common.css")).default]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"]))
], AdminDeleteUserDialog);



/***/ }),

/***/ "./src/app/Modals/ConcurrencyResolver/ConcurrencyResolver.component.ts":
/*!*****************************************************************************!*\
  !*** ./src/app/Modals/ConcurrencyResolver/ConcurrencyResolver.component.ts ***!
  \*****************************************************************************/
/*! exports provided: ConcurrencyResolver */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ConcurrencyResolver", function() { return ConcurrencyResolver; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");





let ConcurrencyResolver = class ConcurrencyResolver {
    constructor(dialogRef, data, http, formBuilder) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.formBuilder = formBuilder;
        this.ConcurrencyDetails = data;
        this.http = http;
        this.dialogRef.disableClose = true;
        this.type = data.patch[0].path.substr(1);
        this.formControl = formBuilder.group({});
        this.clientVersion = data.patch[0].value;
        this.serverVersion = "";
        this.currentEtag = '"0"';
        this.radioOption = "";
        this.fetchFromServer();
    }
    fetchFromServer() {
        this.http.get(this.data.url, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.data.token
            }),
            observe: "response"
        }).subscribe(result => {
            if (this.type === "requirementText") {
                // @ts-ignore
                this.serverVersion = result.body.requirementText;
            }
            else if (this.type === "priority") {
                // @ts-ignore
                this.serverVersion = result.body.priority;
            }
            else {
                console.error("Wrong type!");
                console.error(result.body);
                this.dialogRef.close(null);
            }
            this.currentEtag = result.headers.get('etag');
        }, error => {
            console.error(error);
        });
    }
    updateRequirementPriority(field, update) {
        if (field === "client") {
            this.clientVersion = update;
        }
        else if (field === "server") {
            this.serverVersion = update;
        }
    }
    onNoClick() {
        //The value that was selected
        let value = "";
        if (this.radioOption === "client") {
            value = this.clientVersion;
        }
        else if (this.radioOption === "server") {
            value = this.serverVersion;
        }
        else if (this.radioOption === "new") {
            value = this.newVersion;
        }
        // Creating the patch object
        let patch = [{}];
        if (this.type === "requirementText") {
            patch = [{ op: "replace", path: "/requirementText", value: value }];
        }
        else if (this.type === "priority") {
            patch = [{ op: "replace", path: "/priority", value: value }];
        }
        this.http.patch(this.data.url, patch, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.data.token,
                ETAG: this.currentEtag
            }),
            observe: "response"
        }).subscribe(result => {
            let returned = result.body;
            returned.etag = result.headers.get("etag");
            this.dialogRef.close(returned);
        }, error => {
            console.error(error);
            this.dialogRef.close(null);
        });
    }
};
ConcurrencyResolver.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__["MatDialogRef"] },
    { type: undefined, decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__["MAT_DIALOG_DATA"],] }] },
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"] }
];
ConcurrencyResolver = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'ConcurrencyResolver.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./ConcurrencyResolver.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/ConcurrencyResolver/ConcurrencyResolver.Dialog.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../KravEdit/kravEdit.component.css */ "./src/app/KravEdit/kravEdit.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../common.css */ "./src/app/common.css")).default]
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__["MAT_DIALOG_DATA"]))
], ConcurrencyResolver);



/***/ }),

/***/ "./src/app/Modals/DeleteUser/DeleteUser.component.ts":
/*!***********************************************************!*\
  !*** ./src/app/Modals/DeleteUser/DeleteUser.component.ts ***!
  \***********************************************************/
/*! exports provided: DeleteUserDialog */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DeleteUserDialog", function() { return DeleteUserDialog; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");






let DeleteUserDialog = 
// tslint:disable-next-line:component-class-suffix
class DeleteUserDialog {
    constructor(dialogRef, data, formBuilder, router) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.checked = false;
        this.loading = false;
        this.deleted = false;
        this.error = null;
        this.authorized = 0;
        this.pass = '';
        this.router = router;
        this.userData = JSON.parse(localStorage.getItem('UserData'));
        this.formGroup = formBuilder.group({
            Pass: [this.pass, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required
                ]]
        });
    }
    confirm() {
        this.loading = true;
        this.dialogRef.disableClose = true;
        this.data.delete(this.userData._links.konto.href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_5__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            if (result === null) {
                this.deleted = true;
                this.dialogRef.disableClose = false;
                this.loading = false;
            }
        }, error => {
            this.loading = false;
            this.error = error;
            console.error(error);
        });
    }
    checkPass() {
        this.authorized = 1;
        this.dialogRef.disableClose = true;
        // Checks if the old password is valid (Note that this only occurs clientside and is very easely bypassed)
        let body = new _angular_common_http__WEBPACK_IMPORTED_MODULE_5__["HttpParams"]();
        body = body.set('grant_type', 'password');
        body = body.append('username', this.userData.userName);
        body = body.append('password', this.pass);
        this.data.post(this.userData._links['login OAuth2'].href, body, {
            // Constructs the headers
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_5__["HttpHeaders"]({
                Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + 'secret'),
                'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
            })
        }).subscribe(result => {
            // @ts-ignore
            this.userData.oauthClientSecret = result.access_token;
            this.authorized = 2;
        }, error => {
            if (error.error.error_description === 'Bad credentials') {
                // @ts-ignore
                this.authorized = -1;
            }
            else {
                console.error(error);
            }
        });
    }
    logout() {
        localStorage.clear();
        this.data.get(this.userData._links['logout OAuth2'].href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_5__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
        }, error => {
            console.log(error);
        });
        this.router.navigate(['/']);
        location.reload();
    }
    onNoClick() {
        if (this.deleted) {
            localStorage.clear();
            window.location.reload();
        }
        else if (this.authorized) {
            // If the token has been altered by checking for the correct password it must be updated
            localStorage.setItem('UserData', JSON.stringify(this.userData));
            this.dialogRef.close();
        }
        else {
            this.dialogRef.close();
        }
    }
};
DeleteUserDialog.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__["MatDialogRef"] },
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_5__["HttpClient"], decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__["MAT_DIALOG_DATA"],] }] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"] }
];
DeleteUserDialog = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'DeleteUser.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./DeleteUser.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/DeleteUser/DeleteUser.Dialog.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../UserEdit/userEdit.component.css */ "./src/app/UserEdit/userEdit.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../common.css */ "./src/app/common.css")).default]
    })
    // tslint:disable-next-line:component-class-suffix
    ,
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material_dialog__WEBPACK_IMPORTED_MODULE_4__["MAT_DIALOG_DATA"]))
], DeleteUserDialog);



/***/ }),

/***/ "./src/app/Modals/NewProject/NewProject.component.ts":
/*!***********************************************************!*\
  !*** ./src/app/Modals/NewProject/NewProject.component.ts ***!
  \***********************************************************/
/*! exports provided: NewProjectDialog */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NewProjectDialog", function() { return NewProjectDialog; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");



let NewProjectDialog = 
// tslint:disable-next-line:component-class-suffix
class NewProjectDialog {
    constructor(dialogRef, data) {
        this.dialogRef = dialogRef;
        this.data = data;
    }
    onNoClick() {
        this.dialogRef.close();
    }
};
NewProjectDialog.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MatDialogRef"] },
    { type: undefined, decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"],] }] }
];
NewProjectDialog = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'NewProject.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./NewProject.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/NewProject/NewProject.Dialog.html")).default
    })
    // tslint:disable-next-line:component-class-suffix
    ,
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"]))
], NewProjectDialog);



/***/ }),

/***/ "./src/app/Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component.ts":
/*!*************************************************************************************!*\
  !*** ./src/app/Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component.ts ***!
  \*************************************************************************************/
/*! exports provided: PasswordChangeConfirmedDialog */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PasswordChangeConfirmedDialog", function() { return PasswordChangeConfirmedDialog; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");



let PasswordChangeConfirmedDialog = 
// tslint:disable-next-line:component-class-suffix
class PasswordChangeConfirmedDialog {
    constructor(dialogRef) {
        this.dialogRef = dialogRef;
    }
    onNoClick() {
        this.dialogRef.close();
    }
};
PasswordChangeConfirmedDialog.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MatDialogRef"] }
];
PasswordChangeConfirmedDialog = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'PasswordChangeConfirmed.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./PasswordChangeConfirmed.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/PasswordChangeConfirmed/PasswordChangeConfirmed.Dialog.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../UserEdit/userEdit.component.css */ "./src/app/UserEdit/userEdit.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../common.css */ "./src/app/common.css")).default]
    })
    // tslint:disable-next-line:component-class-suffix
], PasswordChangeConfirmedDialog);



/***/ }),

/***/ "./src/app/Modals/RemoveProject/RemoveProject.compnent.ts":
/*!****************************************************************!*\
  !*** ./src/app/Modals/RemoveProject/RemoveProject.compnent.ts ***!
  \****************************************************************/
/*! exports provided: DeleteProjectDialog */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DeleteProjectDialog", function() { return DeleteProjectDialog; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");



let DeleteProjectDialog = class DeleteProjectDialog {
    constructor(dialogRef, data) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.data = true;
    }
    onNoClick() {
        this.dialogRef.close();
    }
};
DeleteProjectDialog.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MatDialogRef"] },
    { type: Boolean, decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"],] }] }
];
DeleteProjectDialog = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'DeleteProject.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./RemoveProject.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/RemoveProject/RemoveProject.Dialog.html")).default
    }),
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"]))
], DeleteProjectDialog);



/***/ }),

/***/ "./src/app/Modals/RemoveReq/RemoveReq.component.ts":
/*!*********************************************************!*\
  !*** ./src/app/Modals/RemoveReq/RemoveReq.component.ts ***!
  \*********************************************************/
/*! exports provided: DeleteRequirmentDialog */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DeleteRequirmentDialog", function() { return DeleteRequirmentDialog; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");



let DeleteRequirmentDialog = 
// tslint:disable-next-line:component-class-suffix
class DeleteRequirmentDialog {
    constructor(dialogRef, data) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.data = true;
    }
    onNoClick() {
        this.dialogRef.close();
    }
};
DeleteRequirmentDialog.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MatDialogRef"] },
    { type: Boolean, decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"],] }] }
];
DeleteRequirmentDialog = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'DeleteRequirment.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./RemoveReq.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/RemoveReq/RemoveReq.Dialog.html")).default
    })
    // tslint:disable-next-line:component-class-suffix
    ,
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material_dialog__WEBPACK_IMPORTED_MODULE_2__["MAT_DIALOG_DATA"]))
], DeleteRequirmentDialog);



/***/ }),

/***/ "./src/app/Modals/ShareMenu/shareMenu.component.ts":
/*!*********************************************************!*\
  !*** ./src/app/Modals/ShareMenu/shareMenu.component.ts ***!
  \*********************************************************/
/*! exports provided: ShareMenu */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ShareMenu", function() { return ShareMenu; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _models_UserData_model__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../models/UserData.model */ "./src/app/models/UserData.model.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");






let ShareMenu = 
/**
 * ShareMenu
 * The dialog where a user can select other users to partake in the work of a project
 */
class ShareMenu {
    /**
     * ShareMenuConstructor
     * @param dialogRef This dialogrefrence
     * @param data Data wich the dialog can use, in this case the userData
     * @param http The HTTP Client wich the dialog will use to comunicate with the server
     * @param formBuilder The form Builder wich will be used to validate that the user enters valid emails
     */
    constructor(dialogRef, data, http, formBuilder) {
        this.dialogRef = dialogRef;
        this.data = data;
        this.formBuilder = formBuilder;
        this.userData = data;
        this.http = http;
        this.shares = [];
        this.getActiveShares();
        this.formgroup = formBuilder.group({
            email: [this.newShare, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required,
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].email
                ]],
        });
    }
    getActiveShares() {
        this.http.get(this.userData.currentProject._links.access.href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            // @ts-ignore
            this.shares = result._embedded.users;
            // tslint:disable-next-line:no-shadowed-variable
        }, error => {
            console.error(error);
        });
    }
    addShare() {
        this.http.post(this.userData.currentProject._links.share.href.replace('user_email_address', this.newShare), {}, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            this.getActiveShares();
        }, error => {
            console.error(error);
        });
    }
    revokeShare(user) {
        this.http.delete(this.userData.currentProject._links.share.href.replace('user_email_address', user), {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
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
};
ShareMenu.ctorParameters = () => [
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__["MatDialogRef"] },
    { type: _models_UserData_model__WEBPACK_IMPORTED_MODULE_3__["UserData"], decorators: [{ type: _angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"], args: [_angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__["MAT_DIALOG_DATA"],] }] },
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormBuilder"] }
];
ShareMenu = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        // tslint:disable-next-line:component-selector
        selector: 'ShareMenu.Dialog',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./ShareMenu.Dialog.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/Modals/ShareMenu/ShareMenu.Dialog.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../KravEdit/kravEdit.component.css */ "./src/app/KravEdit/kravEdit.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../KravEdit/kravEdit.component.css */ "./src/app/KravEdit/kravEdit.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../../common.css */ "./src/app/common.css")).default]
    })
    /**
     * ShareMenu
     * The dialog where a user can select other users to partake in the work of a project
     */
    ,
    tslib__WEBPACK_IMPORTED_MODULE_0__["__param"](1, Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Inject"])(_angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__["MAT_DIALOG_DATA"]))
], ShareMenu);



/***/ }),

/***/ "./src/app/UserEdit/userEdit.component.css":
/*!*************************************************!*\
  !*** ./src/app/UserEdit/userEdit.component.css ***!
  \*************************************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".main-container{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: center;\r\n          justify-content: center;\r\n  margin-top: 10px;\r\n}\r\n\r\n.cards{\r\n  width: 300px;\r\n  height: 370px;\r\n  margin: 10px;\r\n}\r\n\r\n.user-info-bottom{\r\n  height: 40% !important;\r\n}\r\n\r\n.text-force-black{\r\n  color: black !important;\r\n}\r\n\r\n.pass-panel{\r\n  box-shadow: none;\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvVXNlckVkaXQvdXNlckVkaXQuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLDhCQUFtQjtFQUFuQiw2QkFBbUI7VUFBbkIsbUJBQW1CO0VBQ25CLHdCQUF1QjtVQUF2Qix1QkFBdUI7RUFDdkIsZ0JBQWdCO0FBQ2xCOztBQUVBO0VBQ0UsWUFBWTtFQUNaLGFBQWE7RUFDYixZQUFZO0FBQ2Q7O0FBRUE7RUFDRSxzQkFBc0I7QUFDeEI7O0FBRUE7RUFDRSx1QkFBdUI7QUFDekI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEIiLCJmaWxlIjoic3JjL2FwcC9Vc2VyRWRpdC91c2VyRWRpdC5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLm1haW4tY29udGFpbmVye1xyXG4gIGRpc3BsYXk6IGZsZXg7XHJcbiAgZmxleC1kaXJlY3Rpb246IHJvdztcclxuICBqdXN0aWZ5LWNvbnRlbnQ6IGNlbnRlcjtcclxuICBtYXJnaW4tdG9wOiAxMHB4O1xyXG59XHJcblxyXG4uY2FyZHN7XHJcbiAgd2lkdGg6IDMwMHB4O1xyXG4gIGhlaWdodDogMzcwcHg7XHJcbiAgbWFyZ2luOiAxMHB4O1xyXG59XHJcblxyXG4udXNlci1pbmZvLWJvdHRvbXtcclxuICBoZWlnaHQ6IDQwJSAhaW1wb3J0YW50O1xyXG59XHJcblxyXG4udGV4dC1mb3JjZS1ibGFja3tcclxuICBjb2xvcjogYmxhY2sgIWltcG9ydGFudDtcclxufVxyXG5cclxuLnBhc3MtcGFuZWx7XHJcbiAgYm94LXNoYWRvdzogbm9uZTtcclxufVxyXG4iXX0= */");

/***/ }),

/***/ "./src/app/UserEdit/userEdit.component.ts":
/*!************************************************!*\
  !*** ./src/app/UserEdit/userEdit.component.ts ***!
  \************************************************/
/*! exports provided: userEditComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "userEditComponent", function() { return userEditComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm2015/ngx-translate-core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm2015/material.js");
/* harmony import */ var _Modals_DeleteUser_DeleteUser_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ../Modals/DeleteUser/DeleteUser.component */ "./src/app/Modals/DeleteUser/DeleteUser.component.ts");
/* harmony import */ var _Modals_PasswordChangeConfirmed_PasswordChangeConfimred_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ../Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component */ "./src/app/Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component.ts");










let userEditComponent = 
// tslint:disable-next-line:class-name
class userEditComponent {
    constructor(http, router, formBuilder, translate, snackBar, dialog) {
        this.translate = translate;
        this.http = http;
        this.router = router;
        this.formBuilder = formBuilder;
        this.snackBar = snackBar;
        this.dialogBox = dialog;
        translate.addLangs(['Bokmål', 'English', 'Nynorsk']);
        translate.setDefaultLang('Bokmål');
    }
    ngOnInit() {
        this.userData = JSON.parse(localStorage.getItem('UserData'));
        this.passwordForm = this.formBuilder.group({
            oldP: [this.oldPassword, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required
                ]],
            newP: [this.newPassword, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required
                ]],
            repP: [this.repeatPassword, [
                    _angular_forms__WEBPACK_IMPORTED_MODULE_4__["Validators"].required
                ]]
        });
    }
    logout() {
        localStorage.clear();
        this.http.get(this.userData._links['logout OAuth2'].href, {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
        }, error => {
            console.error(error);
        });
        this.router.navigate(['/']);
        location.reload();
    }
    goToMainMenu() {
        this.userData.nav = 'Menu';
        localStorage.setItem('UserData', JSON.stringify(this.userData));
        this.router.navigate(['/Menu']);
    }
    /**
     * newPasswordSubmit
     *
     * Gets called when the user presses the change password button,
     * checks to se if all requirements are met then sends a request to the server
     */
    newPasswordSubmit() {
        if (this.newPassword !== this.repeatPassword) {
            // @ts-ignore
            this.snackBar.open(this.translate.get('ErrorsAndWarns.PasswordsDontMatch').value, this.translate.get('ErrorsAndWarns.Close').value);
        }
        else {
            // Checks if the old password is valid (Note that this only occurs clientside and is very easely bypassed)
            let body = new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpParams"]();
            body = body.set('grant_type', 'password');
            body = body.append('username', this.userData.userName);
            body = body.append('password', this.oldPassword);
            this.http.post(this.userData._links['login OAuth2'].href, body, {
                // Constructs the headers
                headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                    Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + 'secret'),
                    'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
                })
            }).subscribe(result => {
                this.submittNewPassword();
            }, error => {
                if (error.error.error_description === 'Bad credentials') {
                    // @ts-ignore
                    this.snackBar.open(this.translate.get('ErrorsAndWarns.WrongOldPass').value, this.translate.get('ErrorsAndWarns.Close').value);
                }
                else {
                    console.error(error);
                }
            });
        }
    }
    /**
     * submittNewPassword
     *
     * After all checks have been fulfilled sends a request to the server to update the password of the current user
     */
    submittNewPassword() {
        this.http.patch(this.userData._links.konto.href, [{ op: 'replace', path: '/password', value: this.newPassword }], {
            headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                Authorization: 'Bearer ' + this.userData.oauthClientSecret
            })
        }).subscribe(result => {
            // Sends a request to invalidate the current token
            this.http.get(this.userData._links['logout OAuth2'].href, {
                headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                    Authorization: 'Bearer ' + this.userData.oauthClientSecret
                })
                // tslint:disable-next-line:no-shadowed-variable
            }).subscribe(result => {
                // After the old token has been invalidated, fetches a new one
                let body = new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpParams"]();
                body = body.set('grant_type', 'password');
                body = body.append('username', this.userData.userName);
                body = body.append('password', this.newPassword);
                this.http.post(this.userData._links['login OAuth2'].href, body, {
                    // Constructs the headers
                    headers: new _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpHeaders"]({
                        Authorization: 'Basic ' + btoa(this.userData.oauthClientId + ':' + 'secret'),
                        'Content-type': 'application/x-www-form-urlencoded; charset=utf-8'
                    })
                }).subscribe(
                // tslint:disable-next-line:no-shadowed-variable
                result => {
                    // New token request fullfilled displays a modal and saves the new token
                    // @ts-ignore
                    this.userData.oauthClientSecret = result.access_token;
                    localStorage.setItem('UserData', JSON.stringify(this.userData));
                    this.dialogBox.open(_Modals_PasswordChangeConfirmed_PasswordChangeConfimred_component__WEBPACK_IMPORTED_MODULE_9__["PasswordChangeConfirmedDialog"], {
                        width: '450px'
                    });
                }, error => {
                    console.error(error);
                });
            }, error => {
                console.error(error);
            });
        }, error => {
            console.error(error);
        });
    }
    /**
     * deleteUser
     *
     * Is called when the user presses the delete user button in the GUI, will open a dialog where the user will confirm,
     * the request is also handled from within the dialog
     */
    deleteUser() {
        const ref = this.dialogBox.open(_Modals_DeleteUser_DeleteUser_component__WEBPACK_IMPORTED_MODULE_8__["DeleteUserDialog"], {
            width: '80%',
            maxWidth: '600px',
            data: this.http
        });
        ref.afterClosed().subscribe(result => {
            this.userData = JSON.parse(localStorage.getItem('UserData'));
        });
    }
};
userEditComponent.ctorParameters = () => [
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_2__["HttpClient"] },
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"] },
    { type: _angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormBuilder"] },
    { type: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_6__["TranslateService"] },
    { type: _angular_material__WEBPACK_IMPORTED_MODULE_7__["MatSnackBar"] },
    { type: _angular_material_dialog__WEBPACK_IMPORTED_MODULE_5__["MatDialog"] }
];
userEditComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-root',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./userEdit.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/UserEdit/userEdit.component.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./userEdit.component.css */ "./src/app/UserEdit/userEdit.component.css")).default, tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ../common.css */ "./src/app/common.css")).default]
    })
    // tslint:disable-next-line:class-name
], userEditComponent);



/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _Login_Login_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./Login/Login.component */ "./src/app/Login/Login.component.ts");
/* harmony import */ var _Menu_menu_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./Menu/menu.component */ "./src/app/Menu/menu.component.ts");
/* harmony import */ var _KravEdit_kravEdit_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./KravEdit/kravEdit.component */ "./src/app/KravEdit/kravEdit.component.ts");
/* harmony import */ var _UserEdit_userEdit_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./UserEdit/userEdit.component */ "./src/app/UserEdit/userEdit.component.ts");
/* harmony import */ var _Admin_Admin_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./Admin/Admin.component */ "./src/app/Admin/Admin.component.ts");








const routes = [
    { path: 'Login', component: _Login_Login_component__WEBPACK_IMPORTED_MODULE_3__["LoginComponent"] },
    { path: 'Menu', component: _Menu_menu_component__WEBPACK_IMPORTED_MODULE_4__["MenuComponent"] },
    { path: 'KravEdit', component: _KravEdit_kravEdit_component__WEBPACK_IMPORTED_MODULE_5__["kravEditComponent"] },
    { path: 'userEdit', component: _UserEdit_userEdit_component__WEBPACK_IMPORTED_MODULE_6__["userEditComponent"] },
    { path: 'Admin', component: _Admin_Admin_component__WEBPACK_IMPORTED_MODULE_7__["adminComponent"] }
];
let AppRoutingModule = class AppRoutingModule {
};
AppRoutingModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
        imports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"].forRoot(routes)],
        exports: [_angular_router__WEBPACK_IMPORTED_MODULE_2__["RouterModule"]]
    })
], AppRoutingModule);



/***/ }),

/***/ "./src/app/app.animations.ts":
/*!***********************************!*\
  !*** ./src/app/app.animations.ts ***!
  \***********************************/
/*! exports provided: Animations */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Animations", function() { return Animations; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_animations__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/animations */ "./node_modules/@angular/animations/fesm2015/animations.js");


const Animations = {
    toggleSlide: Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["trigger"])('toggleSlide', [
        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["state"])('login', Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({
            width: '100%',
            opacity: '1',
            visibility: 'visible'
        })),
        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["state"])('register', Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({
            width: '0%',
            opacity: '0',
            visibility: 'hidden'
        })),
        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["transition"])('login => register', [
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["animate"])('0.3s ease-out')
        ]),
        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["transition"])('register => login', [
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["animate"])('0.3s ease-out')
        ])
    ]),
    shake: Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["trigger"])('shake', [
        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["state"])('end', Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({
            transform: 'scale(1)'
        })),
        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["transition"])('* => end', Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["animate"])('750ms', Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["keyframes"])([
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(-1px, 0, 0)', offset: 0.1 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(2px, 0, 0)', offset: 0.2 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(-4px, 0, 0)', offset: 0.3 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(4px, 0, 0)', offset: 0.4 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(-4px, 0, 0)', offset: 0.5 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(4px, 0, 0)', offset: 0.6 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(-4px, 0, 0)', offset: 0.7 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(2px, 0, 0)', offset: 0.8 }),
            Object(_angular_animations__WEBPACK_IMPORTED_MODULE_1__["style"])({ transform: 'translate3d(-1px, 0, 0)', offset: 0.9 }),
        ]))),
    ])
};


/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm2015/router.js");
/* harmony import */ var _common__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./common */ "./src/app/common.ts");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _models_UserData_model__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./models/UserData.model */ "./src/app/models/UserData.model.ts");
/* harmony import */ var _models_link_model__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./models/link.model */ "./src/app/models/link.model.ts");
/* harmony import */ var _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/snack-bar */ "./node_modules/@angular/material/esm2015/snack-bar.js");
/* harmony import */ var _models_links_model__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./models/links.model */ "./src/app/models/links.model.ts");









let AppComponent = class AppComponent {
    constructor(router, http, snackBar) {
        this.router = router;
        this.loading = true;
        this.http = http;
        this.userData = new _models_UserData_model__WEBPACK_IMPORTED_MODULE_5__["UserData"]();
        this.userData._links = new _models_links_model__WEBPACK_IMPORTED_MODULE_8__["Links"]();
        this.snackBar = snackBar;
    }
    ngOnInit() {
        // Uses allready existing variables if the user refreshed the page
        if (localStorage.getItem('UserData') !== null) {
            this.userData = JSON.parse(localStorage.getItem('UserData'));
        }
        else {
            this.userData = new _models_UserData_model__WEBPACK_IMPORTED_MODULE_5__["UserData"]();
        }
        // Runs the function beneath that fetches API info from the server
        this.getApiDetails();
    }
    // Fetches ApiDetails from the server, that is utialized for further communication
    getApiDetails() {
        this.http.get(_common__WEBPACK_IMPORTED_MODULE_3__["startUrl"]).subscribe(result => {
            this.userData._links['login OAuth2'] = new _models_link_model__WEBPACK_IMPORTED_MODULE_6__["Link"]();
            this.userData._links['login OAuth2'].href = result._links['login OAuth2'].href.split('?')[0];
            this.userData._links['create-user'] = result._links['create-user'];
            localStorage.setItem('UserData', JSON.stringify(this.userData));
            this.loading = false;
            this.navigate();
        }, error => {
            console.error(error);
            const ref = this.snackBar.open('Could not reach the Grouse server', 'Try again');
            ref.onAction().subscribe(() => {
                this.getApiDetails();
                this.snackBar.dismiss();
            });
        });
    }
    // Navigates the user to the componenet they were inn if the page is refreshed
    navigate() {
        if (this.userData.nav === '' || this.userData.nav === null) {
            this.router.navigate(['/Login']);
        }
        else {
            this.router.navigate(['/' + this.userData.nav]);
        }
    }
};
AppComponent.ctorParameters = () => [
    { type: _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"] },
    { type: _angular_common_http__WEBPACK_IMPORTED_MODULE_4__["HttpClient"] },
    { type: _angular_material_snack_bar__WEBPACK_IMPORTED_MODULE_7__["MatSnackBar"] }
];
AppComponent = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
        selector: 'app-root',
        template: tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! raw-loader!./app.component.html */ "./node_modules/raw-loader/dist/cjs.js!./src/app/app.component.html")).default,
        styles: [tslib__WEBPACK_IMPORTED_MODULE_0__["__importDefault"](__webpack_require__(/*! ./common.css */ "./src/app/common.css")).default]
    })
], AppComponent);



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: HttpLoaderFactory, AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "HttpLoaderFactory", function() { return HttpLoaderFactory; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm2015/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm2015/material.js");
/* harmony import */ var _angular_material_button__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/material/button */ "./node_modules/@angular/material/esm2015/button.js");
/* harmony import */ var _angular_common_http__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/common/http */ "./node_modules/@angular/common/fesm2015/http.js");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm2015/animations.js");
/* harmony import */ var _angular_material_form_field__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/material/form-field */ "./node_modules/@angular/material/esm2015/form-field.js");
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/material/icon */ "./node_modules/@angular/material/esm2015/icon.js");
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/input */ "./node_modules/@angular/material/esm2015/input.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm2015/forms.js");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _angular_material_toolbar__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/toolbar */ "./node_modules/@angular/material/esm2015/toolbar.js");
/* harmony import */ var _angular_material_list__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/material/list */ "./node_modules/@angular/material/esm2015/list.js");
/* harmony import */ var _angular_material_dialog__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/material/dialog */ "./node_modules/@angular/material/esm2015/dialog.js");
/* harmony import */ var _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! @angular/material/checkbox */ "./node_modules/@angular/material/esm2015/checkbox.js");
/* harmony import */ var _angular_material_menu__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! @angular/material/menu */ "./node_modules/@angular/material/esm2015/menu.js");
/* harmony import */ var _angular_material_sidenav__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! @angular/material/sidenav */ "./node_modules/@angular/material/esm2015/sidenav.js");
/* harmony import */ var _angular_material_tree__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! @angular/material/tree */ "./node_modules/@angular/material/esm2015/tree.js");
/* harmony import */ var _angular_material_button_toggle__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! @angular/material/button-toggle */ "./node_modules/@angular/material/esm2015/button-toggle.js");
/* harmony import */ var _ngx_translate_core__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! @ngx-translate/core */ "./node_modules/@ngx-translate/core/fesm2015/ngx-translate-core.js");
/* harmony import */ var _ngx_translate_http_loader__WEBPACK_IMPORTED_MODULE_22__ = __webpack_require__(/*! @ngx-translate/http-loader */ "./node_modules/@ngx-translate/http-loader/fesm2015/ngx-translate-http-loader.js");
/* harmony import */ var _angular_material_radio__WEBPACK_IMPORTED_MODULE_23__ = __webpack_require__(/*! @angular/material/radio */ "./node_modules/@angular/material/esm2015/radio.js");
/* harmony import */ var _UserEdit_userEdit_component__WEBPACK_IMPORTED_MODULE_24__ = __webpack_require__(/*! ./UserEdit/userEdit.component */ "./src/app/UserEdit/userEdit.component.ts");
/* harmony import */ var _KravEdit_kravEdit_component__WEBPACK_IMPORTED_MODULE_25__ = __webpack_require__(/*! ./KravEdit/kravEdit.component */ "./src/app/KravEdit/kravEdit.component.ts");
/* harmony import */ var _Login_Login_component__WEBPACK_IMPORTED_MODULE_26__ = __webpack_require__(/*! ./Login/Login.component */ "./src/app/Login/Login.component.ts");
/* harmony import */ var _Menu_menu_component__WEBPACK_IMPORTED_MODULE_27__ = __webpack_require__(/*! ./Menu/menu.component */ "./src/app/Menu/menu.component.ts");
/* harmony import */ var _Admin_Admin_component__WEBPACK_IMPORTED_MODULE_28__ = __webpack_require__(/*! ./Admin/Admin.component */ "./src/app/Admin/Admin.component.ts");
/* harmony import */ var _Modals_AdminDeleteUser_AdminDeleteUser_component__WEBPACK_IMPORTED_MODULE_29__ = __webpack_require__(/*! ./Modals/AdminDeleteUser/AdminDeleteUser.component */ "./src/app/Modals/AdminDeleteUser/AdminDeleteUser.component.ts");
/* harmony import */ var _Modals_ConcurrencyResolver_ConcurrencyResolver_component__WEBPACK_IMPORTED_MODULE_30__ = __webpack_require__(/*! ./Modals/ConcurrencyResolver/ConcurrencyResolver.component */ "./src/app/Modals/ConcurrencyResolver/ConcurrencyResolver.component.ts");
/* harmony import */ var _Modals_DeleteUser_DeleteUser_component__WEBPACK_IMPORTED_MODULE_31__ = __webpack_require__(/*! ./Modals/DeleteUser/DeleteUser.component */ "./src/app/Modals/DeleteUser/DeleteUser.component.ts");
/* harmony import */ var _Modals_NewProject_NewProject_component__WEBPACK_IMPORTED_MODULE_32__ = __webpack_require__(/*! ./Modals/NewProject/NewProject.component */ "./src/app/Modals/NewProject/NewProject.component.ts");
/* harmony import */ var _Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_33__ = __webpack_require__(/*! ./Modals/RemoveProject/RemoveProject.compnent */ "./src/app/Modals/RemoveProject/RemoveProject.compnent.ts");
/* harmony import */ var _Modals_PasswordChangeConfirmed_PasswordChangeConfimred_component__WEBPACK_IMPORTED_MODULE_34__ = __webpack_require__(/*! ./Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component */ "./src/app/Modals/PasswordChangeConfirmed/PasswordChangeConfimred.component.ts");
/* harmony import */ var _Modals_ShareMenu_shareMenu_component__WEBPACK_IMPORTED_MODULE_35__ = __webpack_require__(/*! ./Modals/ShareMenu/shareMenu.component */ "./src/app/Modals/ShareMenu/shareMenu.component.ts");
/* harmony import */ var _Modals_RemoveReq_RemoveReq_component__WEBPACK_IMPORTED_MODULE_36__ = __webpack_require__(/*! ./Modals/RemoveReq/RemoveReq.component */ "./src/app/Modals/RemoveReq/RemoveReq.component.ts");



// tslint:disable-next-line:max-line-length


































function HttpLoaderFactory(http) {
    return new _ngx_translate_http_loader__WEBPACK_IMPORTED_MODULE_22__["TranslateHttpLoader"](http);
}
let AppModule = class AppModule {
};
AppModule = tslib__WEBPACK_IMPORTED_MODULE_0__["__decorate"]([
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["NgModule"])({
        declarations: [
            _app_component__WEBPACK_IMPORTED_MODULE_12__["AppComponent"],
            _Login_Login_component__WEBPACK_IMPORTED_MODULE_26__["LoginComponent"],
            _Menu_menu_component__WEBPACK_IMPORTED_MODULE_27__["MenuComponent"],
            _Modals_NewProject_NewProject_component__WEBPACK_IMPORTED_MODULE_32__["NewProjectDialog"],
            _Login_Login_component__WEBPACK_IMPORTED_MODULE_26__["GDPRContent"],
            _UserEdit_userEdit_component__WEBPACK_IMPORTED_MODULE_24__["userEditComponent"],
            _Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_33__["DeleteProjectDialog"],
            _Modals_PasswordChangeConfirmed_PasswordChangeConfimred_component__WEBPACK_IMPORTED_MODULE_34__["PasswordChangeConfirmedDialog"],
            _Modals_DeleteUser_DeleteUser_component__WEBPACK_IMPORTED_MODULE_31__["DeleteUserDialog"],
            _Admin_Admin_component__WEBPACK_IMPORTED_MODULE_28__["adminComponent"],
            _Modals_AdminDeleteUser_AdminDeleteUser_component__WEBPACK_IMPORTED_MODULE_29__["AdminDeleteUserDialog"],
            _KravEdit_kravEdit_component__WEBPACK_IMPORTED_MODULE_25__["kravEditComponent"],
            _Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_33__["DeleteProjectDialog"],
            _Modals_ShareMenu_shareMenu_component__WEBPACK_IMPORTED_MODULE_35__["ShareMenu"],
            _Modals_ConcurrencyResolver_ConcurrencyResolver_component__WEBPACK_IMPORTED_MODULE_30__["ConcurrencyResolver"],
            _Modals_RemoveReq_RemoveReq_component__WEBPACK_IMPORTED_MODULE_36__["DeleteRequirmentDialog"]
        ],
        imports: [
            _angular_platform_browser__WEBPACK_IMPORTED_MODULE_1__["BrowserModule"],
            _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_6__["BrowserAnimationsModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatSliderModule"],
            _angular_material_button__WEBPACK_IMPORTED_MODULE_4__["MatButtonModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatCardModule"],
            _angular_material_form_field__WEBPACK_IMPORTED_MODULE_7__["MatFormFieldModule"],
            _angular_material_icon__WEBPACK_IMPORTED_MODULE_8__["MatIconModule"],
            _angular_material_input__WEBPACK_IMPORTED_MODULE_9__["MatInputModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_10__["ReactiveFormsModule"],
            _angular_common_http__WEBPACK_IMPORTED_MODULE_5__["HttpClientModule"],
            _app_routing_module__WEBPACK_IMPORTED_MODULE_11__["AppRoutingModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatProgressSpinnerModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatSnackBarModule"],
            _angular_material_toolbar__WEBPACK_IMPORTED_MODULE_13__["MatToolbarModule"],
            _angular_material_list__WEBPACK_IMPORTED_MODULE_14__["MatListModule"],
            _angular_material_dialog__WEBPACK_IMPORTED_MODULE_15__["MatDialogModule"],
            _angular_forms__WEBPACK_IMPORTED_MODULE_10__["FormsModule"],
            _angular_material_menu__WEBPACK_IMPORTED_MODULE_17__["MatMenuModule"],
            _angular_material_checkbox__WEBPACK_IMPORTED_MODULE_16__["MatCheckboxModule"],
            _angular_material_sidenav__WEBPACK_IMPORTED_MODULE_18__["MatSidenavModule"],
            _angular_material_tree__WEBPACK_IMPORTED_MODULE_19__["MatTreeModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatTabsModule"],
            _angular_material_button_toggle__WEBPACK_IMPORTED_MODULE_20__["MatButtonToggleModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatTooltipModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatProgressBarModule"],
            _ngx_translate_core__WEBPACK_IMPORTED_MODULE_21__["TranslateModule"].forRoot({
                loader: {
                    provide: _ngx_translate_core__WEBPACK_IMPORTED_MODULE_21__["TranslateLoader"],
                    useFactory: HttpLoaderFactory,
                    deps: [_angular_common_http__WEBPACK_IMPORTED_MODULE_5__["HttpClient"]]
                }
            }),
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatSelectModule"],
            _angular_material__WEBPACK_IMPORTED_MODULE_3__["MatExpansionModule"],
            _angular_material_radio__WEBPACK_IMPORTED_MODULE_23__["MatRadioModule"]
        ],
        providers: [],
        bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_12__["AppComponent"]],
        exports: [
            _Modals_NewProject_NewProject_component__WEBPACK_IMPORTED_MODULE_32__["NewProjectDialog"],
            _Login_Login_component__WEBPACK_IMPORTED_MODULE_26__["GDPRContent"],
            _Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_33__["DeleteProjectDialog"],
            _Modals_PasswordChangeConfirmed_PasswordChangeConfimred_component__WEBPACK_IMPORTED_MODULE_34__["PasswordChangeConfirmedDialog"],
            _Modals_DeleteUser_DeleteUser_component__WEBPACK_IMPORTED_MODULE_31__["DeleteUserDialog"],
            _Modals_ShareMenu_shareMenu_component__WEBPACK_IMPORTED_MODULE_35__["ShareMenu"],
            _Modals_AdminDeleteUser_AdminDeleteUser_component__WEBPACK_IMPORTED_MODULE_29__["AdminDeleteUserDialog"],
        ],
        entryComponents: [
            _Modals_NewProject_NewProject_component__WEBPACK_IMPORTED_MODULE_32__["NewProjectDialog"],
            _Login_Login_component__WEBPACK_IMPORTED_MODULE_26__["GDPRContent"],
            _Modals_RemoveReq_RemoveReq_component__WEBPACK_IMPORTED_MODULE_36__["DeleteRequirmentDialog"],
            _Modals_RemoveProject_RemoveProject_compnent__WEBPACK_IMPORTED_MODULE_33__["DeleteProjectDialog"],
            _Modals_PasswordChangeConfirmed_PasswordChangeConfimred_component__WEBPACK_IMPORTED_MODULE_34__["PasswordChangeConfirmedDialog"],
            _Modals_ShareMenu_shareMenu_component__WEBPACK_IMPORTED_MODULE_35__["ShareMenu"],
            _Modals_AdminDeleteUser_AdminDeleteUser_component__WEBPACK_IMPORTED_MODULE_29__["AdminDeleteUserDialog"],
            _Modals_ConcurrencyResolver_ConcurrencyResolver_component__WEBPACK_IMPORTED_MODULE_30__["ConcurrencyResolver"],
            _Modals_RemoveReq_RemoveReq_component__WEBPACK_IMPORTED_MODULE_36__["DeleteRequirmentDialog"],
            _Modals_DeleteUser_DeleteUser_component__WEBPACK_IMPORTED_MODULE_31__["DeleteUserDialog"]
        ]
    })
], AppModule);



/***/ }),

/***/ "./src/app/common.css":
/*!****************************!*\
  !*** ./src/app/common.css ***!
  \****************************/
/*! exports provided: default */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony default export */ __webpack_exports__["default"] = (".Grouse-Avatar{\r\n  background-image: url('Grouse.png');\r\n  background-size: cover;\r\n  height: 45px;\r\n  width: 45px;\r\n  border-radius: 50%;\r\n  margin: 10px;\r\n}\r\n\r\n.flex-center{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-pack: center;\r\n          justify-content: center;\r\n  -webkit-box-align: center;\r\n          align-items: center;}\r\n\r\n.flex-column{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-pack: space-evenly;\r\n          justify-content: space-evenly;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: column;\r\n  height: 100%;\r\n}\r\n\r\n.flex-column-start{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-pack: start;\r\n          justify-content: flex-start;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: column;\r\n  height: 100%;\r\n}\r\n\r\n.flex-column-end{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-pack: end;\r\n          justify-content: flex-end;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: column;\r\n  height: 100%;\r\n}\r\n\r\n.flex-column-squish{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  justify-content: space-around;\r\n  -webkit-box-orient: vertical;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: column;\r\n  height: 20%;\r\n  padding-top: 80px;\r\n  padding-bottom: 60px;\r\n}\r\n\r\n.flex-row-right{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: end;\r\n          justify-content: flex-end;\r\n  flex-wrap: nowrap;\r\n}\r\n\r\n.flex-row-left{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: start;\r\n          justify-content: flex-start;\r\n  flex-wrap: nowrap;\r\n}\r\n\r\n.flex-row-between{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: justify;\r\n          justify-content: space-between;\r\n}\r\n\r\n.flex-row{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: center;\r\n          justify-content: center;\r\n  flex-wrap: nowrap;\r\n}\r\n\r\n.flex-row-evenlyer{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: space-evenly;\r\n          justify-content: space-evenly;\r\n}\r\n\r\n.mal-container{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-direction: row;\r\n  -webkit-box-pack: start;\r\n          justify-content: flex-start;\r\n  flex-wrap: wrap;\r\n  padding-left: 4%;\r\n}\r\n\r\n.mal-card{\r\n  height: 150px;\r\n  width: 300px;\r\n  margin-right: 4%;\r\n\r\n  margin-top: 20px;\r\n}\r\n\r\n.logo-container{\r\n  display: -webkit-box;\r\n  display: flex;\r\n  -webkit-box-orient: horizontal;\r\n  -webkit-box-direction: normal;\r\n          flex-flow: row nowrap;\r\n  -webkit-box-align: center;\r\n          align-items: center;\r\n}\r\n\r\n.no-m{\r\n  margin: 0px;\r\n}\r\n\r\n.no-p{\r\n  padding: 0px;\r\n}\r\n\r\n.p-10{\r\n  padding: 10px;\r\n}\r\n\r\n.p-20{\r\n  padding: 20px;\r\n}\r\n\r\n.m-lr-10{\r\n  margin-left: 10px;\r\n  margin-right: 10px;\r\n}\r\n\r\n.m-t-10{\r\n  margin-top: 10px;\r\n}\r\n\r\n.m-t-14{\r\n  margin-top: 14px;\r\n}\r\n\r\n.icon-large{\r\n  height: 50px;\r\n  width: 50px;\r\n  font-size: 40px;\r\n}\r\n\r\n.bold{\r\n  font-weight: bold;\r\n}\r\n\r\n.fill{\r\n  width: 100%;\r\n  height: 100%;\r\n}\r\n\r\nbody{\r\n  overflow-y: hidden;\r\n}\r\n\r\n.w-100{\r\n  width: 100%;\r\n}\r\n\r\n.h-80{\r\n  height: 80%;\r\n}\r\n\r\n.h-100{\r\n  height: 100%;\r\n}\r\n\r\n.color-lightgray{\r\n  background-color: rgb(220, 220, 220);\r\n}\r\n\r\n.rounded{\r\n  border-radius: 10px;\r\n}\r\n\r\n.m-b-20{\r\n  margin-bottom: 20px;\r\n}\r\n\r\n.w-70{\r\n  width: 70%;\r\n}\r\n\r\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tbW9uLmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLG1DQUE4QztFQUM5QyxzQkFBc0I7RUFDdEIsWUFBWTtFQUNaLFdBQVc7RUFDWCxrQkFBa0I7RUFDbEIsWUFBWTtBQUNkOztBQUVBO0VBQ0Usb0JBQWE7RUFBYixhQUFhO0VBQ2Isd0JBQXVCO1VBQXZCLHVCQUF1QjtFQUN2Qix5QkFBbUI7VUFBbkIsbUJBQW1CLENBQUM7O0FBRXRCO0VBQ0Usb0JBQWE7RUFBYixhQUFhO0VBQ2IsOEJBQTZCO1VBQTdCLDZCQUE2QjtFQUM3Qiw0QkFBc0I7RUFBdEIsNkJBQXNCO1VBQXRCLHNCQUFzQjtFQUN0QixZQUFZO0FBQ2Q7O0FBRUE7RUFDRSxvQkFBYTtFQUFiLGFBQWE7RUFDYix1QkFBMkI7VUFBM0IsMkJBQTJCO0VBQzNCLDRCQUFzQjtFQUF0Qiw2QkFBc0I7VUFBdEIsc0JBQXNCO0VBQ3RCLFlBQVk7QUFDZDs7QUFFQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLHFCQUF5QjtVQUF6Qix5QkFBeUI7RUFDekIsNEJBQXNCO0VBQXRCLDZCQUFzQjtVQUF0QixzQkFBc0I7RUFDdEIsWUFBWTtBQUNkOztBQUVBO0VBQ0Usb0JBQWE7RUFBYixhQUFhO0VBQ2IsNkJBQTZCO0VBQzdCLDRCQUFzQjtFQUF0Qiw2QkFBc0I7VUFBdEIsc0JBQXNCO0VBQ3RCLFdBQVc7RUFDWCxpQkFBaUI7RUFDakIsb0JBQW9CO0FBQ3RCOztBQUVBO0VBQ0Usb0JBQWE7RUFBYixhQUFhO0VBQ2IsOEJBQW1CO0VBQW5CLDZCQUFtQjtVQUFuQixtQkFBbUI7RUFDbkIscUJBQXlCO1VBQXpCLHlCQUF5QjtFQUN6QixpQkFBaUI7QUFDbkI7O0FBRUE7RUFDRSxvQkFBYTtFQUFiLGFBQWE7RUFDYiw4QkFBbUI7RUFBbkIsNkJBQW1CO1VBQW5CLG1CQUFtQjtFQUNuQix1QkFBMkI7VUFBM0IsMkJBQTJCO0VBQzNCLGlCQUFpQjtBQUNuQjs7QUFFQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLDhCQUFtQjtFQUFuQiw2QkFBbUI7VUFBbkIsbUJBQW1CO0VBQ25CLHlCQUE4QjtVQUE5Qiw4QkFBOEI7QUFDaEM7O0FBR0E7RUFDRSxvQkFBYTtFQUFiLGFBQWE7RUFDYiw4QkFBbUI7RUFBbkIsNkJBQW1CO1VBQW5CLG1CQUFtQjtFQUNuQix3QkFBdUI7VUFBdkIsdUJBQXVCO0VBQ3ZCLGlCQUFpQjtBQUNuQjs7QUFFQTtFQUNFLG9CQUFhO0VBQWIsYUFBYTtFQUNiLDhCQUFtQjtFQUFuQiw2QkFBbUI7VUFBbkIsbUJBQW1CO0VBQ25CLDhCQUE2QjtVQUE3Qiw2QkFBNkI7QUFDL0I7O0FBRUE7RUFDRSxvQkFBYTtFQUFiLGFBQWE7RUFDYiw4QkFBbUI7RUFBbkIsNkJBQW1CO1VBQW5CLG1CQUFtQjtFQUNuQix1QkFBMkI7VUFBM0IsMkJBQTJCO0VBQzNCLGVBQWU7RUFDZixnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxhQUFhO0VBQ2IsWUFBWTtFQUNaLGdCQUFnQjs7RUFFaEIsZ0JBQWdCO0FBQ2xCOztBQUVBO0VBQ0Usb0JBQWE7RUFBYixhQUFhO0VBQ2IsOEJBQXFCO0VBQXJCLDZCQUFxQjtVQUFyQixxQkFBcUI7RUFDckIseUJBQW1CO1VBQW5CLG1CQUFtQjtBQUNyQjs7QUFFQTtFQUNFLFdBQVc7QUFDYjs7QUFFQTtFQUNFLFlBQVk7QUFDZDs7QUFFQTtFQUNFLGFBQWE7QUFDZjs7QUFFQTtFQUNFLGFBQWE7QUFDZjs7QUFFQTtFQUNFLGlCQUFpQjtFQUNqQixrQkFBa0I7QUFDcEI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxnQkFBZ0I7QUFDbEI7O0FBRUE7RUFDRSxZQUFZO0VBQ1osV0FBVztFQUNYLGVBQWU7QUFDakI7O0FBRUE7RUFDRSxpQkFBaUI7QUFDbkI7O0FBRUE7RUFDRSxXQUFXO0VBQ1gsWUFBWTtBQUNkOztBQUVBO0VBQ0Usa0JBQWtCO0FBQ3BCOztBQUVBO0VBQ0UsV0FBVztBQUNiOztBQUVBO0VBQ0UsV0FBVztBQUNiOztBQUVBO0VBQ0UsWUFBWTtBQUNkOztBQUVBO0VBQ0Usb0NBQW9DO0FBQ3RDOztBQUVBO0VBQ0UsbUJBQW1CO0FBQ3JCOztBQUVBO0VBQ0UsbUJBQW1CO0FBQ3JCOztBQUVBO0VBQ0UsVUFBVTtBQUNaIiwiZmlsZSI6InNyYy9hcHAvY29tbW9uLmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5Hcm91c2UtQXZhdGFye1xyXG4gIGJhY2tncm91bmQtaW1hZ2U6IHVybCgnc3JjL2Fzc2V0cy9Hcm91c2UucG5nJyk7XHJcbiAgYmFja2dyb3VuZC1zaXplOiBjb3ZlcjtcclxuICBoZWlnaHQ6IDQ1cHg7XHJcbiAgd2lkdGg6IDQ1cHg7XHJcbiAgYm9yZGVyLXJhZGl1czogNTAlO1xyXG4gIG1hcmdpbjogMTBweDtcclxufVxyXG5cclxuLmZsZXgtY2VudGVye1xyXG4gIGRpc3BsYXk6IGZsZXg7XHJcbiAganVzdGlmeS1jb250ZW50OiBjZW50ZXI7XHJcbiAgYWxpZ24taXRlbXM6IGNlbnRlcjt9XHJcblxyXG4uZmxleC1jb2x1bW57XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBqdXN0aWZ5LWNvbnRlbnQ6IHNwYWNlLWV2ZW5seTtcclxuICBmbGV4LWRpcmVjdGlvbjogY29sdW1uO1xyXG4gIGhlaWdodDogMTAwJTtcclxufVxyXG5cclxuLmZsZXgtY29sdW1uLXN0YXJ0e1xyXG4gIGRpc3BsYXk6IGZsZXg7XHJcbiAganVzdGlmeS1jb250ZW50OiBmbGV4LXN0YXJ0O1xyXG4gIGZsZXgtZGlyZWN0aW9uOiBjb2x1bW47XHJcbiAgaGVpZ2h0OiAxMDAlO1xyXG59XHJcblxyXG4uZmxleC1jb2x1bW4tZW5ke1xyXG4gIGRpc3BsYXk6IGZsZXg7XHJcbiAganVzdGlmeS1jb250ZW50OiBmbGV4LWVuZDtcclxuICBmbGV4LWRpcmVjdGlvbjogY29sdW1uO1xyXG4gIGhlaWdodDogMTAwJTtcclxufVxyXG5cclxuLmZsZXgtY29sdW1uLXNxdWlzaHtcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIGp1c3RpZnktY29udGVudDogc3BhY2UtYXJvdW5kO1xyXG4gIGZsZXgtZGlyZWN0aW9uOiBjb2x1bW47XHJcbiAgaGVpZ2h0OiAyMCU7XHJcbiAgcGFkZGluZy10b3A6IDgwcHg7XHJcbiAgcGFkZGluZy1ib3R0b206IDYwcHg7XHJcbn1cclxuXHJcbi5mbGV4LXJvdy1yaWdodHtcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIGZsZXgtZGlyZWN0aW9uOiByb3c7XHJcbiAganVzdGlmeS1jb250ZW50OiBmbGV4LWVuZDtcclxuICBmbGV4LXdyYXA6IG5vd3JhcDtcclxufVxyXG5cclxuLmZsZXgtcm93LWxlZnR7XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBmbGV4LWRpcmVjdGlvbjogcm93O1xyXG4gIGp1c3RpZnktY29udGVudDogZmxleC1zdGFydDtcclxuICBmbGV4LXdyYXA6IG5vd3JhcDtcclxufVxyXG5cclxuLmZsZXgtcm93LWJldHdlZW57XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBmbGV4LWRpcmVjdGlvbjogcm93O1xyXG4gIGp1c3RpZnktY29udGVudDogc3BhY2UtYmV0d2VlbjtcclxufVxyXG5cclxuXHJcbi5mbGV4LXJvd3tcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIGZsZXgtZGlyZWN0aW9uOiByb3c7XHJcbiAganVzdGlmeS1jb250ZW50OiBjZW50ZXI7XHJcbiAgZmxleC13cmFwOiBub3dyYXA7XHJcbn1cclxuXHJcbi5mbGV4LXJvdy1ldmVubHllcntcclxuICBkaXNwbGF5OiBmbGV4O1xyXG4gIGZsZXgtZGlyZWN0aW9uOiByb3c7XHJcbiAganVzdGlmeS1jb250ZW50OiBzcGFjZS1ldmVubHk7XHJcbn1cclxuXHJcbi5tYWwtY29udGFpbmVye1xyXG4gIGRpc3BsYXk6IGZsZXg7XHJcbiAgZmxleC1kaXJlY3Rpb246IHJvdztcclxuICBqdXN0aWZ5LWNvbnRlbnQ6IGZsZXgtc3RhcnQ7XHJcbiAgZmxleC13cmFwOiB3cmFwO1xyXG4gIHBhZGRpbmctbGVmdDogNCU7XHJcbn1cclxuXHJcbi5tYWwtY2FyZHtcclxuICBoZWlnaHQ6IDE1MHB4O1xyXG4gIHdpZHRoOiAzMDBweDtcclxuICBtYXJnaW4tcmlnaHQ6IDQlO1xyXG5cclxuICBtYXJnaW4tdG9wOiAyMHB4O1xyXG59XHJcblxyXG4ubG9nby1jb250YWluZXJ7XHJcbiAgZGlzcGxheTogZmxleDtcclxuICBmbGV4LWZsb3c6IHJvdyBub3dyYXA7XHJcbiAgYWxpZ24taXRlbXM6IGNlbnRlcjtcclxufVxyXG5cclxuLm5vLW17XHJcbiAgbWFyZ2luOiAwcHg7XHJcbn1cclxuXHJcbi5uby1we1xyXG4gIHBhZGRpbmc6IDBweDtcclxufVxyXG5cclxuLnAtMTB7XHJcbiAgcGFkZGluZzogMTBweDtcclxufVxyXG5cclxuLnAtMjB7XHJcbiAgcGFkZGluZzogMjBweDtcclxufVxyXG5cclxuLm0tbHItMTB7XHJcbiAgbWFyZ2luLWxlZnQ6IDEwcHg7XHJcbiAgbWFyZ2luLXJpZ2h0OiAxMHB4O1xyXG59XHJcblxyXG4ubS10LTEwe1xyXG4gIG1hcmdpbi10b3A6IDEwcHg7XHJcbn1cclxuXHJcbi5tLXQtMTR7XHJcbiAgbWFyZ2luLXRvcDogMTRweDtcclxufVxyXG5cclxuLmljb24tbGFyZ2V7XHJcbiAgaGVpZ2h0OiA1MHB4O1xyXG4gIHdpZHRoOiA1MHB4O1xyXG4gIGZvbnQtc2l6ZTogNDBweDtcclxufVxyXG5cclxuLmJvbGR7XHJcbiAgZm9udC13ZWlnaHQ6IGJvbGQ7XHJcbn1cclxuXHJcbi5maWxse1xyXG4gIHdpZHRoOiAxMDAlO1xyXG4gIGhlaWdodDogMTAwJTtcclxufVxyXG5cclxuYm9keXtcclxuICBvdmVyZmxvdy15OiBoaWRkZW47XHJcbn1cclxuXHJcbi53LTEwMHtcclxuICB3aWR0aDogMTAwJTtcclxufVxyXG5cclxuLmgtODB7XHJcbiAgaGVpZ2h0OiA4MCU7XHJcbn1cclxuXHJcbi5oLTEwMHtcclxuICBoZWlnaHQ6IDEwMCU7XHJcbn1cclxuXHJcbi5jb2xvci1saWdodGdyYXl7XHJcbiAgYmFja2dyb3VuZC1jb2xvcjogcmdiKDIyMCwgMjIwLCAyMjApO1xyXG59XHJcblxyXG4ucm91bmRlZHtcclxuICBib3JkZXItcmFkaXVzOiAxMHB4O1xyXG59XHJcblxyXG4ubS1iLTIwe1xyXG4gIG1hcmdpbi1ib3R0b206IDIwcHg7XHJcbn1cclxuXHJcbi53LTcwe1xyXG4gIHdpZHRoOiA3MCU7XHJcbn1cclxuIl19 */");

/***/ }),

/***/ "./src/app/common.ts":
/*!***************************!*\
  !*** ./src/app/common.ts ***!
  \***************************/
/*! exports provided: startUrl, REL_PROJECT, REL_SELF, REL_REQUIREMENT, REL_DOCUMENT, REL_FUNCTIONALITY, REL_LOGIN_OAUTH, REL_LOGOUT_OAUTH, REL_USER */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "startUrl", function() { return startUrl; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_PROJECT", function() { return REL_PROJECT; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_SELF", function() { return REL_SELF; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_REQUIREMENT", function() { return REL_REQUIREMENT; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_DOCUMENT", function() { return REL_DOCUMENT; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_FUNCTIONALITY", function() { return REL_FUNCTIONALITY; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_LOGIN_OAUTH", function() { return REL_LOGIN_OAUTH; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_LOGOUT_OAUTH", function() { return REL_LOGOUT_OAUTH; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "REL_USER", function() { return REL_USER; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");

const startUrl = 'https://nikita.oslomet.no/grouse2/';
// Rels
let REL_PROJECT = 'prosjekt';
let REL_SELF = 'self';
let REL_REQUIREMENT = 'krav';
let REL_DOCUMENT = 'dokument';
let REL_FUNCTIONALITY = 'funksjon';
let REL_LOGIN_OAUTH = 'login OAuth2';
let REL_LOGOUT_OAUTH = 'logout OAuth2';
let REL_USER = 'konto';


/***/ }),

/***/ "./src/app/models/UserData.model.ts":
/*!******************************************!*\
  !*** ./src/app/models/UserData.model.ts ***!
  \******************************************/
/*! exports provided: UserData */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UserData", function() { return UserData; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var _links_model__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./links.model */ "./src/app/models/links.model.ts");


class UserData {
    constructor() {
        this.userName = '';
        this.oauthClientId = 'grouse-client';
        this.oauthClientSecret = 'secret';
        this.nav = '';
        this._links = new _links_model__WEBPACK_IMPORTED_MODULE_1__["Links"]();
        this.currentProject = null;
        this.defaultLang = 'Bokmål';
    }
}


/***/ }),

/***/ "./src/app/models/link.model.ts":
/*!**************************************!*\
  !*** ./src/app/models/link.model.ts ***!
  \**************************************/
/*! exports provided: Link */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Link", function() { return Link; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");

class Link {
    constructor() {
    }
}


/***/ }),

/***/ "./src/app/models/links.model.ts":
/*!***************************************!*\
  !*** ./src/app/models/links.model.ts ***!
  \***************************************/
/*! exports provided: Links */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Links", function() { return Links; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");

class Links {
    constructor() {
    }
}


/***/ }),

/***/ "./src/app/models/login.model.ts":
/*!***************************************!*\
  !*** ./src/app/models/login.model.ts ***!
  \***************************************/
/*! exports provided: LoginModel */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginModel", function() { return LoginModel; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");

class LoginModel {
}


/***/ }),

/***/ "./src/app/models/register.model.ts":
/*!******************************************!*\
  !*** ./src/app/models/register.model.ts ***!
  \******************************************/
/*! exports provided: RegisterModel */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "RegisterModel", function() { return RegisterModel; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");

class RegisterModel {
}


/***/ }),

/***/ "./src/app/models/statusPageData.model.ts":
/*!************************************************!*\
  !*** ./src/app/models/statusPageData.model.ts ***!
  \************************************************/
/*! exports provided: statusPageData */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "statusPageData", function() { return statusPageData; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");

class statusPageData {
    constructor() {
        this.progress = 0;
        this.loaded = true;
        this.unfinished = null;
        this.finished = true;
        this.generatingDocument = false;
        this.sportedFormats = [];
        this.selectedFormat = null;
    }
}


/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

const environment = {
    production: false
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var tslib__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tslib */ "./node_modules/tslib/tslib.es6.js");
/* harmony import */ var hammerjs__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! hammerjs */ "./node_modules/hammerjs/hammer.js");
/* harmony import */ var hammerjs__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(hammerjs__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm2015/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm2015/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");






if (_environments_environment__WEBPACK_IMPORTED_MODULE_5__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_3__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_4__["AppModule"])
    .catch(err => console.error(err));


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! Z:\Documents\Repos\grouse\web\src\main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main-es2015.js.map