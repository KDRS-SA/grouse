import {projectFunctionality} from './projectFunctionality.model';

export class statusPageData {
  progress: number;
  unfinished: projectFunctionality[];
  loaded: boolean;
  finished: boolean;
  generatingDocument: boolean;

  constructor() {
    this.progress = 0;
    this.loaded = true;
    this.unfinished = null;
    this.finished = true;
    this.generatingDocument = false;
  }
}
