package ro.sopa.statistifier.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.sopa.statistifier.db.model.TrackListen;

import java.time.ZonedDateTime;
import java.util.List;

@org.springframework.stereotype.Repository
public interface TrackListenRepository extends JpaRepository<TrackListen, Long> {

    @Query("select max(t.date) from TrackListen t where t.username=:username")
    ZonedDateTime findLatestListenDate(@Param("username") String username);

    @Query("select min(t.date) from TrackListen t where t.username=:username")
    ZonedDateTime findOldestListenDate(@Param("username") String username);

    @Query(value = "select t.artist, count(1) as c from Track_Listen t where t.username = :username group by t.artist order by c desc limit :number", nativeQuery = true)
    List<Object[]> findTopArtists_mysql(@Param("username") String username, @Param("number") Integer number);

    @Query(value = "select top (:number) t.artist, count(1) as c from Track_Listen t where t.username = :username group by t.artist order by c desc", nativeQuery = true)
    List<Object[]> findTopArtists_mssql(@Param("username") String username, @Param("number") Integer number);

    default List<Object[]> findTopArtists(@Param("username") String username, @Param("number") Integer number, String dbType) {
        if("mysql".equals(dbType)) {
            return findTopArtists_mysql(username, number);
        }
        if("mssql".equals(dbType)) {
            return findTopArtists_mssql(username, number);
        }
        throw new RuntimeException("Unknown db type: " + dbType);
    }

    @Query("select t.date from TrackListen t where t.username = :username and artist = :artist and date >= :minDate and date <= :maxDate")
    List<ZonedDateTime> findListenDates(@Param("username") String username, @Param("artist") String artist, @Param("minDate")  ZonedDateTime minDate, @Param("maxDate") ZonedDateTime maxDate);

}
