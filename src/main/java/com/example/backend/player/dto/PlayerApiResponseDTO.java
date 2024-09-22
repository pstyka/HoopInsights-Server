package com.example.backend.player.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerApiResponseDTO {
    private List<PlayerDTO> response;

    @Getter
    @Setter
    public static class PlayerDTO {
        private Long id;
        private String firstname;
        private String lastname;
        private BirthDTO birth;
        private NbaDTO nba;
        private HeightDTO height;
        private WeightDTO weight;
        private LeaguesDTO leagues;

        @Getter
        @Setter
        public static class BirthDTO {
            private String date;
            private String country;
        }

        @Getter
        @Setter
        public static class NbaDTO {
            private int start;
            private int pro;
        }

        @Getter
        @Setter
        public static class HeightDTO {
            private String feets;
            private String inches;
            private String meters;
        }

        @Getter
        @Setter
        public static class WeightDTO {
            private String pounds;
            private String kilograms;
        }

        @Getter
        @Setter
        public static class LeaguesDTO {
            private StandardDTO standard;

            @Getter
            @Setter
            public static class StandardDTO {
                private String jersey;
                private Boolean active;
                private String pos;
            }
        }
    }
}

