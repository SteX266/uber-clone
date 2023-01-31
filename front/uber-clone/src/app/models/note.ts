export class Note {
  id: number;
  text: string;
  date: Date;
  user: number;

  constructor(id: number, text: string, date: Date, user: number) {
    this.id = id;
    this.text = text;
    this.date = date;
    this.user = user;
  }
}
