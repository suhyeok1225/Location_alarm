package honeyimleaving.toyproject.honeyimleaving.retrofit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*이 코드는 Retrofit 라이브러리를 사용하여 Google Places API에서 받아온 장소 정보를 담는 ResponsePlace 클래스를 정의한 것입니다.
        ResponsePlace 클래스는 candidates와 status 두 개의 멤버 변수를 가지고 있습니다.
        candidates는 장소 정보를 담고 있는 Candidates 클래스의 리스트를 나타내며, status는 API 요청 결과를 나타내는 문자열입니다.
        ResponsePlace 클래스는 getCandidates() 메소드를 가지고 있으며, 이 메소드는 candidates 리스트를 반환합니다.
        또한, @SerializedName 어노테이션을 사용하여 candidates와 status 멤버 변수가 JSON에서 어떤 이름으로 표현되는지를 지정해줍니다.
        이를 통해 Retrofit 라이브러리가 JSON 데이터를 자동으로 파싱할 수 있습니다.

 */

public class ResponsePlace {
    @SerializedName("candidates")    List<Candidates> candidates;
    @SerializedName("status")  String status;

    public List<Candidates> getCandidates() {
        return candidates;
    }
}
