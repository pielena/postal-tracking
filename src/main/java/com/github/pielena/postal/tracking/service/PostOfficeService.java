package com.github.pielena.postal.tracking.service;

import com.github.pielena.postal.tracking.entity.PostOffice;

public interface PostOfficeService {

    PostOffice findByIndex(int index);
}
