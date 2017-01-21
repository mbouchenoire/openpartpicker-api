package com.mbouchenoire.openpartpicker.api.domain;

/**
 * @author mbouchenoire
 */
public final class PartLink {

    private final String title;
    private final String url;

    public PartLink(String title, String url) {
        if (title == null)
            throw new IllegalArgumentException("title");

        if (url == null)
            throw new IllegalArgumentException("url");

        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PartLink partLink = (PartLink) o;

        return title.equals(partLink.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public String toString() {
        return "PartLink{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
