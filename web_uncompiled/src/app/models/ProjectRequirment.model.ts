import {legacyLink} from './linkLegacy.model';
import {Links} from './links.model';

export class ProjectRequirment {
  projectRequirmentId: number;
  order: number;
  requirementText: string;
  priority: string;
  ownedBy: string;
  links: legacyLink[];
  _links: Links;
  etag: string;
}
