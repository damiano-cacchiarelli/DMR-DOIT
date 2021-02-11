import { Tag } from "../../modello/progetto/tag";

export class TagListDto {
    tags: Tag[];

    constructor(tags: Tag[]){
        this.tags = tags;
    }
}
