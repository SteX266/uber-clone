import { Component, Input, Output, EventEmitter } from '@angular/core';
import { RideState } from 'src/app/models/ride-state-enum.model';

@Component({
  selector: 'app-driver-sidebar',
  templateUrl: './driver-sidebar.component.html',
  styleUrls: ['./driver-sidebar.component.scss'],
})
export class DriverSidebarComponent {
  @Input() rideState!: RideState;

  @Input() available!: boolean;

  @Output() onToggleAvailable = new EventEmitter();

  @Output() onRejectRide = new EventEmitter();

  @Output() onStartRide = new EventEmitter();

  @Output() onEndRide = new EventEmitter();
}
