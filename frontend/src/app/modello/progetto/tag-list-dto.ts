import { Tag } from "./tag";

export class TagListDto {
    tags: Tag[];

    constructor(tags: Tag[]){
        this.tags = tags;
    }
}
