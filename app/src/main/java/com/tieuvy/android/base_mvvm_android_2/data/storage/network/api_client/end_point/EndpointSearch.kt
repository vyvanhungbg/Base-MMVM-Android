package com.tieuvy.android.base_mvvm_android_2.data.storage.network.api_client.end_point

import io.ktor.resources.Resource
/***

https://api.ktor.io/ktor-client/ktor-client-plugins/ktor-client-resources/io.ktor.client.plugins.resources/-resources/index.html
 ***/

@Resource("/search")
class EndpointSearch() {

    @Resource("/users") // with query parameters : /search/users?q=abc
    class UserName(val q: String? = "abc", val parent: EndpointSearch = EndpointSearch(),)
}