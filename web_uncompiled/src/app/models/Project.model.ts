import {Links} from './links.model';

export class Project{
  projectId: string;
  projectName: string;
  createdDate: string;
  lastModifiedDate: string;
  ownedBy: string;
  _links: Links;

  constructor(projectId: string, projectName: string, organisationName: string, createdDate: string, ownedBy: string) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.createdDate = createdDate;
    this.lastModifiedDate = createdDate;
    this.ownedBy = ownedBy;
  }
}
