import { AbstractControl } from "@angular/forms";

export function phoneNoValidation(control: AbstractControl): { [key: string]: boolean } | null {
    let numberRegEx = /^\d{10}$/;

    if (control.value && control.value !== '' && !numberRegEx.test(control.value)) {
        return { 'invalidPhoneNo': true };
    }
    return null;
}

export function uaePhoneNoValidation(control: AbstractControl): { [key: string]: boolean } | null {
    let numberRegEx = /^\d{15}$/;

    if (control.value && control.value !== '' && !numberRegEx.test(control.value)) {
        return { 'invalidPhoneNo': true };
    }
    return null;
}

export function customPhoneNoValidation(control: AbstractControl): { [key: string]: boolean } | null {
    let numberRegEx = /^\d{7,15}$/;

    if (control.value && control.value !== '' && !numberRegEx.test(control.value)) {
        return { 'invalidPhoneNo': true };
    }
    return null;
}

export function floatingNumberValidation(control: AbstractControl): { [key: string]: boolean } | null {
    let numberRegEx = /^(\d*\.)?\d+$/im;

    if (control.value && control.value !== '' && !numberRegEx.test(control.value)) {
        return { 'invalidFloatingNo': true };
    }
    return null;
}

export function numberValidation(control: AbstractControl): { [key: string]: boolean } | null {
    let numberRegEx = /^\d+$/im;

    if (control.value && control.value !== '' && !numberRegEx.test(control.value)) {
        return { 'invalidNo': true };
    }
    return null;
}

export function isValidEmail(control: AbstractControl): any {
    let emailRegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (control.value && control.value !== '' && !emailRegExp.test(control.value)) {
        return {
            invalidEmail: true
        }
    }
}

export function isValidUrlWebsite(control: AbstractControl): any {
    let urlRegex = /(http(s)?:\/\/.)?(www\.)?[-a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&\/=]*)/g;
    if (control.value && control.value !== '' && !urlRegex.test(control.value)) {
        return {
            invalidUrl: true
        }
    }
}



export function isValidUrl(control: AbstractControl): any {
    let urlRegex = /((http|https)?:\/\/.)/g;
    if (control.value && control.value !== '' && !urlRegex.test(control.value)) {
        return {
            invalidUrl: true
        }
    }
}