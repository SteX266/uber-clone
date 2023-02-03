import { Location } from '@angular/common';
import { Component } from '@angular/core';
import { ActivatedRoute, Route } from '@angular/router';
import { Note } from 'src/app/models/note';
import { NoteService } from 'src/app/services/note/note.service';
import { SnackBarService } from 'src/app/services/snackbar/snackbar.service';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.scss'],
})
export class NotesComponent {
  notes: Note[] = [];
  newNote = new Note(0, '', new Date(), 2);
  selectedId = 0;

  constructor(
    private noteService: NoteService,
    private route: ActivatedRoute,
    private snackBarService: SnackBarService,
    private location: Location
  ) {}

  ngOnInit() {
    this.route.params.subscribe((params) => {
      let id = params['id'];
      this.selectedId = Number(id);
    });
    this.noteService.getUserNotes(this.selectedId).subscribe((value) => {
      this.notes = value;
    });
  }

  addNote() {
    if (this.newNote.text !== '') {
      this.newNote.date = new Date();
      let n = new Note(
        2,
        this.newNote.text,
        new Date(),
        Number(this.selectedId)
      );
      this.noteService.postNote(n).subscribe();
      this.snackBarService.openSuccessSnackBar('Successfully added note');
      this.back();
    }
  }
  back(): void {
    this.location.back();
  }
  formateDate(date: any) {
    return (
      date[3] + ':' + date[4] + ' ' + date[1] + '/' + date[2] + '/' + date[0]
    );
  }
}
