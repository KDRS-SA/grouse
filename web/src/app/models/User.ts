import {Links} from './links.model';

export class User {
  username: string;
  accountNonExpired: boolean;
  credentialsNonExpired: boolean;
  accountNonLocked: boolean;
  enabled: boolean
  // tslint:disable-next-line:variable-name
  _links: Links;
}
