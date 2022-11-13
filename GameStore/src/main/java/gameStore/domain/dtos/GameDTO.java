package gameStore.domain.dtos;

import gameStore.domain.entities.Game;

import java.math.BigDecimal;
import java.time.LocalDate;

import static gameStore.constants.Validations.*;

public class GameDTO {

    private String title;

    private String trailerId;

    private String imageThumbnail;

    private float size;

    private BigDecimal price;

    private String description;

    private LocalDate releaseDate;

    public GameDTO() {
    }

    public GameDTO(String title, String trailerId, String imageThumbnail, float size, BigDecimal price, String description, LocalDate releaseDate) {
        setTitle(title);
        setTrailerId(trailerId);
        setImageThumbnail(imageThumbnail);
        setSize(size);
        setPrice(price);
        setDescription(description);
        setReleaseDate(releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title != null && !Character.isUpperCase(title.charAt(0))
                || title.length() < 3 || title.length() > 100) {
            throw new IllegalArgumentException(INVALID_GAME_TITLE);
        }
        this.title = title;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public void setTrailerId(String trailerId) {
        if (trailerId != null && !(trailerId.length() == 11)) {
            throw new IllegalArgumentException(INCORRECT_TRAILER_ID);
        }
        this.trailerId = trailerId;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {

        if (imageThumbnail != null && !imageThumbnail.startsWith("http://")
                || !imageThumbnail.startsWith("https://")) {
            throw new IllegalArgumentException("error");
        }
        this.imageThumbnail = imageThumbnail;
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        if (size < 0.0) {
            throw new IllegalArgumentException(SIZE_NOT_VALID);
        }
        this.size = size;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.longValue() < 0) {
            throw new IllegalArgumentException(PRICE_NOT_VALID);
        }
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description != null && description.length() < 20) {
            throw new IllegalArgumentException(DESCRIPTION_LENGTH);
        }
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Game toGame() {
      return new Game(title, trailerId, imageThumbnail, size, price, description, releaseDate);
    }

}
