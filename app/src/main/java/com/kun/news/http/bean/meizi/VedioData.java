/*
 * Copyright (C) 2015 Drakeet <drakeet.me@gmail.com>
 *
 * This file is part of Meizhi
 *
 * Meizhi is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Meizhi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Meizhi.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kun.news.http.bean.meizi;

import java.util.ArrayList;

/**
 * 中文名是故意的，请勿吐槽[笑哭]
 * Created by drakeet on 8/15/15.
 */
public class VedioData extends BaseData {

    public ArrayList<Gank> results;

    public ArrayList<Gank> getResults() {
        return results;
    }

    public void setResults(ArrayList<Gank> results) {
        this.results = results;
    }
}