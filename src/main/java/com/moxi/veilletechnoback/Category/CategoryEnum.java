package com.moxi.veilletechnoback.Category;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum CategoryEnum {
	front,back,tools,devops,database,other
}
