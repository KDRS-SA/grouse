import {Link} from './link.model';

export class Links {
  self: Link;
  project: Link;
  function: Link;
  krav: Link;
  dokument: Link;
  ['login OAuth2']: Link;
  konto: Link;
  ['project-list']: Link;
  ['template-list']: Link;
  ['logout OAuth2']: Link;
  ['project-list-all']: Link;
  ['template-list-all']: Link;
  requirement: Link;

  constructor() {
    this.self = new Link();
    this.project = new Link();
    this.function = new Link();
    this. krav = new Link();
    this.dokument = new Link();
    this['login OAuth2'] = new Link();
    this['project-list'] = new Link();
    this['logout OAuth2'] = new Link();
    this['template-list'] = new Link();
    this['project-list-all'] = new Link();
    this['template-list-all'] = new Link();
    this.requirement = new Link();
  }
}
