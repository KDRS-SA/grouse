import {Links} from './links.model';

export class Template {
  templateId: string;
  templateName: string;
  createdDate: string;
  lastModifiedData: string;
  ownedBy: string;
  // tslint:disable-next-line:variable-name
  _links: Links;

  constructor() {
    this.templateId = '';
    this.templateName = 'MISINGNAME';
    this.createdDate = '';
    this.lastModifiedData = '';
    this.ownedBy = 'NOOWNER';
    this._links = new Links();
  }
}
