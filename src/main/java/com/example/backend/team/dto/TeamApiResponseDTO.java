package com.example.backend.team.dto;

import java.util.List;

public class TeamApiResponseDTO {

    private String get;
    private Parameters parameters;
    private List<TeamDTO> response;
    private List<String> errors;
    private int results;

    // Getters and Setters
    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public List<TeamDTO> getResponse() {
        return response;
    }

    public void setResponse(List<TeamDTO> response) {
        this.response = response;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public static class Parameters {
        private String id;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class TeamDTO {
        private Integer id;
        private String name;
        private String nickname;
        private String code;
        private String city;
        private String logo;
        private boolean allStar;
        private boolean nbaFranchise;
        private Leagues leagues;

        private Integer apiId;

        // Getters and Setters
        public Integer getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public boolean isAllStar() {
            return allStar;
        }

        public void setAllStar(boolean allStar) {
            this.allStar = allStar;
        }

        public boolean isNbaFranchise() {
            return nbaFranchise;
        }

        public void setNbaFranchise(boolean nbaFranchise) {
            this.nbaFranchise = nbaFranchise;
        }

        public Leagues getLeagues() {
            return leagues;
        }

        public void setLeagues(Leagues leagues) {
            this.leagues = leagues;
        }

        public Integer getApiId() {
            return apiId;
        }

        public void setApiId(Integer apiId) {
            this.apiId = apiId;
        }

        public static class Leagues {
            private Conference standard;
            private Conference vegas;
            private Conference utah;
            private Conference sacramento;

            // Getters and Setters
            public Conference getStandard() {
                return standard;
            }

            public void setStandard(Conference standard) {
                this.standard = standard;
            }

            public Conference getVegas() {
                return vegas;
            }

            public void setVegas(Conference vegas) {
                this.vegas = vegas;
            }

            public Conference getUtah() {
                return utah;
            }

            public void setUtah(Conference utah) {
                this.utah = utah;
            }

            public Conference getSacramento() {
                return sacramento;
            }

            public void setSacramento(Conference sacramento) {
                this.sacramento = sacramento;
            }

            public static class Conference {
                private String conference;
                private String division;

                public String getConference() {
                    return conference;
                }

                public void setConference(String conference) {
                    this.conference = conference;
                }

                public String getDivision() {
                    return division;
                }

                public void setDivision(String division) {
                    this.division = division;
                }
            }
        }
    }
}
