import {projectFunctionality} from './projectFunctionality.model';
import {IFormat} from "../KravEdit/kravEdit.component";

export class statusPageData {
  progress: number;
  unfinished: projectFunctionality[];
  loaded: boolean;
  finished: boolean;
  generatingDocument: boolean;
  sportedFormats: IFormat[];
  selectedFormat: IFormat;

  constructor() {
    this.progress = 0;
    this.loaded = true;
    this.unfinished = null;
    this.finished = true;
    this.generatingDocument = false;
    this.sportedFormats = [];
    this.selectedFormat = null;
  }
}
