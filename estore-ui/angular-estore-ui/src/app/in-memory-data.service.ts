import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class InMemoryDataService {

  constructor() { }

  private storageKey = '';

  setUserText(text: string): void {
    this.storageKey = text;
  }

  getUserText(): string {
    return this.storageKey;
  }
}
