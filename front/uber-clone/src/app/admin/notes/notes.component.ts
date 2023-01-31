import { Component } from '@angular/core';
import { Note } from 'src/app/models/note';
import { NoteService } from 'src/app/services/note/note.service';

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
  styleUrls: ['./notes.component.scss'],
})
export class NotesComponent {
  notes: Note[] = [];
  newNote = new Note(0, '', new Date(), 2);

  constructor(private noteService: NoteService) {}

  ngOnInit() {
    this.noteService.getUserNotes(3).subscribe((value) => {
      this.notes = value;
    });
  }

  addNote() {
    if (this.newNote.text !== '') {
      this.newNote.date = new Date();
      let n = new Note(1, this.newNote.text, new Date(), 3);
      this.noteService.postNote(n).subscribe();
      this.notes.push(n);
      this.newNote.text = '';
    }
  }
}
