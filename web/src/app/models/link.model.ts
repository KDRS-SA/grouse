export class Link {
  href: string;
  rel: string;

  constructor(rel: string, href: string) {
    this.href = href;
    this.rel = rel;
  }
}
