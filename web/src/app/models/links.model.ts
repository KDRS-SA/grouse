import {Link} from './link.model';

export class Links {
  self: Link;
  prosjekt: Link;
  funksjon: Link;
  krav: Link;
  dokument: Link;

  constructor() {
    this.self = new Link();
    this.prosjekt = new Link();
    this.funksjon = new Link();
    this. krav = new Link();
    this.dokument = new Link();
  }
}
