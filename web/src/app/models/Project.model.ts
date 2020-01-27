import {Link} from './link.model';

export class Project{
  projectId: number;
  projectName: string;
  organisationName: string;
  fileName: string;
  filenameInternal: string;
  documentCreated: boolean;
  projectComplete: string;
  createdDate: string;
  changedDate: string;
  ownedBy: string;
  links: Link[];

  constructor(projectId: number, projectName: string, organisationName: string, createdDate: string, ownedBy: string) {
    this.projectId = projectId;
    this.projectName = projectName;
    this.organisationName = organisationName;
    this.fileName = projectName + '.docx';
    this.documentCreated = false;
    this.createdDate = createdDate;
    this.changedDate = createdDate;
    this.ownedBy = ownedBy;
  }
}
