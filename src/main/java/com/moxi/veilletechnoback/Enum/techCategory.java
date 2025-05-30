package com.moxi.veilletechnoback.Enum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum techCategory {
	front,back,tools,devops,database,other
}
