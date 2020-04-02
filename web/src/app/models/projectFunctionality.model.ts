import {Links} from './links.model';
import {ProjectRequirment} from './ProjectRequirment.model';

export class projectFunctionality{
  projectFunctionalityId: number;
  functionalityNumber: string;
  title: string;
  description: string;
  consequence: string;
  explanation: string;
  processed: boolean;
  type: string;
  referenceChildProjectFunctionality: projectFunctionality[];
  referenceProjectRequirement: ProjectRequirment[];
  parent: projectFunctionality;
  _links: Links;

  constructor() {}
}
