import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'noImg'
})
export class NoImgPipe implements PipeTransform {

  transform(image: string | null | undefined): string {

    if (!image) {
      return 'assets/img/noimage.png';
    }

    return image;
  }

}
