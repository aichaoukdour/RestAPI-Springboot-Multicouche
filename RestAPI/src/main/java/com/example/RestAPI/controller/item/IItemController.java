package com.example.RestAPI.controller.item;

import com.example.RestAPI.controller.IController;
import com.example.RestAPI.dto.ItemRequest;
import com.example.RestAPI.dto.ItemResponse;

interface IItemController extends IController<ItemRequest, ItemResponse, Long> {

}