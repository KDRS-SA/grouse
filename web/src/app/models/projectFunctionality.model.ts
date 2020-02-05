import {Link} from "./link.model";
import {Links} from './links.model';

export class projectFunctionality{
  projectFunctionalityId: string;
  functionalityNumber: number;
  title: string;
  description: string;
  consequence: string;
  explanation: string;
  showMe: boolean;
  processed: boolean;
  active: boolean;
  type: string;
  ownedBy: string;
  referenceChildProjectFunctionality: projectFunctionality[];
  referenceProjectRequirement: projectFunctionality[];
  _links: Links;

  constructor() {}
}
