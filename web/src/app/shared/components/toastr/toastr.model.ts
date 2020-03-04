export class Toastr {
    id: string;
    type: ToastrType;
    message: string;
    autoClose: boolean;
    keepAfterRouteChange: boolean;
    fade: boolean;
  
    constructor(init?:Partial<Toastr>) {
        Object.assign(this, init);
    }
  }
  
  export enum ToastrType {
    Success,
    Error,
    Info,
    Warning
  }
  