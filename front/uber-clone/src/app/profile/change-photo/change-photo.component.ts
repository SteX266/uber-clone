import { Component, Input } from '@angular/core';
import { Location } from '@angular/common';
import { Image } from 'src/app/models/Image';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { UserService } from 'src/app/services/user/user.service';
@Component({
  selector: 'app-change-photo',
  templateUrl: './change-photo.component.html',
  styleUrls: ['./change-photo.component.scss'],
})
export class ChangePhotoComponent {
  @Input() srcData: SafeResourceUrl | undefined;

    constructor(private location: Location, private userService:UserService,private sanitizer: DomSanitizer ) {}

  back(): void {
    this.location.back();
  }
  picture:string = '';
  removePhoto() {}

  addPhoto(fileInput: any) {
    const fileRead = fileInput.target.files[0];
    let picturePath = new FileReader();
    picturePath.readAsDataURL(fileRead);
    let that = this;
    picturePath.onload = (e) => {
      if (e.target != null) {
        let i = new Image(e.target.result as string, fileRead.name);
        that.userService.addImage(i).subscribe({
          next: (data) => {
            this.picture = URL.createObjectURL(data);
            this.srcData = this.sanitizer.bypassSecurityTrustUrl(this.picture);
          },
        });

      }
    };
  }
}
