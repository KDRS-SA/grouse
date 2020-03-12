import {Link} from './link.model';

export class Links {
  self: Link;
  project: Link;
  function: Link;
  krav: Link;
  dokument: Link;
  ['login OAuth2']: Link;

  constructor() {
    this.self = new Link();
    this.project = new Link();
    this.function = new Link();
    this. krav = new Link();
    this.dokument = new Link();
  }
}
