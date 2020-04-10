package com.zl.way.mp.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;

/**
 * WayQueryArticlePostResp
 */
@Validated
public class WayQueryArticlePostResp {
    private Integer total = null;

    @Valid
    private List<WayQueryArticlePost> items = null;

    public WayQueryArticlePostResp total(Integer total) {
        this.total = total;
        return this;
    }

    /**
     * Get total
     * 
     * @return total
     **/

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public WayQueryArticlePostResp items(List<WayQueryArticlePost> items) {
        this.items = items;
        return this;
    }

    public WayQueryArticlePostResp addItemsItem(WayQueryArticlePost itemsItem) {
        if (this.items == null) {
            this.items = new ArrayList<WayQueryArticlePost>();
        }
        this.items.add(itemsItem);
        return this;
    }

    /**
     * Get items
     * 
     * @return items
     **/
    @Valid
    public List<WayQueryArticlePost> getItems() {
        return items;
    }

    public void setItems(List<WayQueryArticlePost> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WayQueryArticlePostResp wayQueryArticlePostResp = (WayQueryArticlePostResp)o;
        return Objects.equals(this.total, wayQueryArticlePostResp.total)
            && Objects.equals(this.items, wayQueryArticlePostResp.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(total, items);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class WayQueryArticlePostResp {\n");

        sb.append("    total: ").append(toIndentedString(total)).append("\n");
        sb.append("    items: ").append(toIndentedString(items)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
