import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RideState } from 'src/app/models/ride-state-enum.model';
import { ShiftService } from 'src/app/services/shift/shift.service';

@Component({
  selector: 'app-driver-sidebar',
  templateUrl: './driver-sidebar.component.html',
  styleUrls: ['./driver-sidebar.component.scss'],
})
export class DriverSidebarComponent {
  constructor(private shiftService: ShiftService) {}
  reason: string = '';
  @Input() rideState!: RideState;

  @Input() available!: boolean;

  @Output() onToggleAvailable = new EventEmitter();

  @Output() onRejectRide = new EventEmitter();

  @Output() onStartRide = new EventEmitter();

  @Output() onEndRide = new EventEmitter();

  toggleAvailable() {
    this.available = !this.available;
    console.log(this.available);
    this.shiftService
      .toggleAvailable(this.available)
      .subscribe((data: any) => {});
  }
}
