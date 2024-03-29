package app.dao;

import app.entities.Marker;
import java.util.Optional;

public interface MarkerDao extends BaseDao<Marker> {
    Optional<Marker> getMarkerByTweetId (long tweetId);
}
