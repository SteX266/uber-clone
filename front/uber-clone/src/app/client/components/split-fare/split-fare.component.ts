import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-split-fare',
  templateUrl: './split-fare.component.html',
  styleUrls: ['./split-fare.component.scss'],
})
export class SplitFareComponent {
  @Output() onAdd = new EventEmitter();

  @Output() onRemove = new EventEmitter();
  people: string[] = [];
  field: string = '';

  addPerson() {
    console.log(this.field);
    if (!this.field) return;
    this.people.push(this.field);
    this.onAdd.emit(this.field);
    this.field = '';
  }

  removePerson(i: number) {
    this.onRemove.emit(i);
    this.people.splice(i, 1);
  }

  customTrackBy(index: number, obj: any): any {
    return index;
  }
}
