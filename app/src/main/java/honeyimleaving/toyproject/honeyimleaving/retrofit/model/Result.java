package honeyimleaving.toyproject.honeyimleaving.retrofit.model;

import java.util.List;


/*이 코드는 Retrofit 라이브러리를 사용하여 Google Reverse Geocoding API에서 받아온 주소 정보를 담는 Result 클래스를 정의한 것입니다.
        Result 클래스는 formatted_address, geometry, place_id, types 네 개의 멤버 변수를 가지고 있습니다.
        formatted_address는 주소를 나타내며, geometry는 주소의 위치와 뷰포트 정보를 담고 있습니다.
        place_id는 장소의 고유 ID를 나타내며, types는 장소의 타입을 나타냅니다.
        Geometry 클래스는 Rect와 LatLng 두 개의 내부 클래스를 가지고 있습니다.
        Rect 클래스는 뷰포트의 북동쪽과 남서쪽 좌표를 담고 있으며, LatLng 클래스는 위도와 경도 정보를 담고 있습니다.
        마지막으로, toString() 메소드는 Result 객체를 문자열로 변환하여 반환합니다. 반환되는 문자열은 주소를 나타냅니다.

 */
public class Result {
    String formatted_address;
    Geometry geometry;
    String place_id;
    List<String> types;

    static class Geometry {
        Rect bounds;
        LatLng location;
        String location_type;
        Rect viewport;

        static class Rect {
            LatLng northeast, southwest;
        }

        static class LatLng {
            double lat, lng;
        }

        public Rect getBounds() {
            return bounds;
        }

        public LatLng getLocation() {
            return location;
        }

        public String getLocation_type() {
            return location_type;
        }

        public Rect getViewport() {
            return viewport;
        }
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public Geometry.LatLng getGeometryLocation() {
        return geometry.getLocation();
    }

    public String getPlace_id() {
        return place_id;
    }

    public List<String> getTypes() {
        return types;
    }

    @Override
    public String toString() {
        return formatted_address;
    }
}
