import {Links} from './links.model';

export class Project{
  projectId: number;
  projectName: string;
  createdDate: string;
  lastModifiedDate: string;
  ownedBy: string;
  _links: Links;

  constructor(projectId: number, projectName: string, organisationName: string, createdDate: string, ownedBy: string) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.createdDate = createdDate;
    this.lastModifiedDate = createdDate;
    this.ownedBy = ownedBy;
  }
}
