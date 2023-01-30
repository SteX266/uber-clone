import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PreviewData } from 'src/app/models/preview-data.model';

@Component({
  selector: 'app-reservation-preview',
  templateUrl: './reservation-preview.component.html',
  styleUrls: ['./reservation-preview.component.scss'],
})
export class ReservationPreviewComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<ReservationPreviewComponent>,
    @Inject(MAT_DIALOG_DATA) public data: PreviewData
  ) {}
  time!: string;
  ngOnInit(): void {
    this.time = this.prettyTime(this.data.time);
  }

  prettyTime(time: number) {
    var minutesInSeconds = time * 60;
    var wholeMinutes = Math.floor(time);
    var remainingSeconds = Math.round(minutesInSeconds - wholeMinutes * 60);
    return wholeMinutes + ':' + remainingSeconds.toString().padStart(2, '0');
  }
}
