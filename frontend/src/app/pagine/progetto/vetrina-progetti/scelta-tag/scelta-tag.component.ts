import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { Tag } from 'src/app/modello/progetto/tag';
import { TagListDto } from 'src/app/modello/progetto/tag-list-dto';
import { TagService } from 'src/app/servizi/tag.service';

@Component({
  selector: 'scelta-tag',
  templateUrl: './scelta-tag.component.html',
  styleUrls: ['./scelta-tag.component.css']
})
export class SceltaTagComponent implements OnInit {

  tags: Map<Tag, boolean> = new Map();

  constructor(
    private tagService: TagService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {
    this.tagService.tuttiITag().subscribe(
      data => {
        this.tags = new Map();
        data.forEach((tag: Tag) => {
          this.tags.set(tag, false);
        });
      },
      err => {
        this.toastr.error(err.error.messaggio, "Errore", {
          timeOut: 3000, positionClass: "toast-top-center"
        });
      }
    );
  }

  addTag(tag: Tag): void {
    this.tags.set(tag, !this.tags.get(tag));
  }

  hasTags(): boolean {
    return this.tags.size > 0;
  }

  getTagsScelti(): TagListDto {
    let tagsScelti: Tag[] = [];
    this.tags.forEach((value: boolean, key: Tag) => {
      if (value) tagsScelti.push(key);
    });
    return new TagListDto(tagsScelti);
  }
}
