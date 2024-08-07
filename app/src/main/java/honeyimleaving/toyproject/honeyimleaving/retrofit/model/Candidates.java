package honeyimleaving.toyproject.honeyimleaving.retrofit.model;


/*이 코드는 Retrofit 라이브러리를 사용하여 Google Places API에서 받아온 장소 정보를 담는 모델 클래스인 Candidates를 정의한 것입니다.
        Candidates 클래스는 formatted_address, geometry, name 세 개의 멤버 변수를 가지고 있습니다.
        formatted_address는 장소의 주소를 나타내며, geometry는 장소의 위치와 뷰포트 정보를 담고 있습니다.
        name은 장소의 이름을 나타냅니다.
        Geometry 클래스는 LatLng과 Rect 두 개의 내부 클래스를 가지고 있습니다.
        LatLng 클래스는 위도와 경도 정보를 담고 있으며, Rect 클래스는 뷰포트의 북동쪽과 남서쪽 좌표를 담고 있습니다.
        Candidates 클래스는 getPlaceName(), getLatitude(), getLongitude(), getFormattedAddress() 네 개의 메소드를 가지고 있습니다.
        getPlaceName()은 장소의 이름을 반환하며, getLatitude()와 getLongitude()는 위도와 경도 정보를 반환합니다.
        getFormattedAddress()는 장소의 주소를 반환합니다.
        마지막으로, toString() 메소드는 Candidates 객체를 문자열로 변환하여 반환합니다.
        반환되는 문자열은 장소의 이름과 주소를 포함합니다.

 */
public class Candidates {
    String formatted_address;
    Candidates.Geometry geometry;
    String name;

    static class Geometry {
        Result.Geometry.LatLng location;
        Result.Geometry.Rect viewport;

        static class Rect {
            Candidates.Geometry.LatLng northeast, southwest;
        }

        static class LatLng {
            double lat, lng;
        }

        public Result.Geometry.Rect getViewport() {
            return viewport;
        }

        public double getLatitude() {
            return location.lat;
        }
        public double getLongitude() {
            return location.lng;
        }
    }

    public String getPlaceName() {
        return name;
    }

    public double getLatitude() {
        return  geometry.getLatitude();
    }
    public double getLongitude() {
        return  geometry.getLongitude();
    }

    public String getFormattedAddress() {
        return formatted_address;
    }

    @Override
    public String toString() {
        return name + "\n(" + formatted_address +")" ;
    }
}
