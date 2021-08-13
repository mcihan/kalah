const DEFAULT_PIT_COUNT = 6;
const DEFAULT_STONE_COUNT = 4;
const PIT_START_ID = 1;
const PIT_END_ID = DEFAULT_PIT_COUNT * 2 + 2;
const PIT_MEDIAN_ID = PIT_END_ID / 2;
const HOUSE_A_PIT_ID = PIT_MEDIAN_ID;
const HOUSE_B_PIT_ID = PIT_END_ID;
const TOTAL_STONE_COUNT = DEFAULT_STONE_COUNT * (PIT_MEDIAN_ID - 1) * 2;

angular.module('KalahGameApplication', [])
    .controller('GameController', function ($scope, $http) {
        let gameController = this;
        $scope.gameId = undefined;
        $scope.gameUrl = undefined;
        $scope.gameStatus = undefined;
        $scope.activePlayer = undefined;
        $scope.errorMessage = undefined;
        $scope.gameStatus = "START";

        gameController.setFields = function (moveResponse) {
            $scope.pitMap = moveResponse.status;
            $scope.pits = Object.entries($scope.pitMap).map(([id, stone]) => ({id, stone}));
            $scope.houseAStone = $scope.pitMap[HOUSE_A_PIT_ID];
            $scope.houseBStone = $scope.pitMap[HOUSE_B_PIT_ID];
            $scope.pitsA = $scope.pits.filter(p => p.id < PIT_MEDIAN_ID);
            $scope.pitsB = $scope.pits.filter(p => p.id > PIT_MEDIAN_ID).filter(p => p.id < PIT_END_ID).reverse();
            $scope.houses = $scope.pits.filter(p => p.id % PIT_MEDIAN_ID === 0).reverse();
        };

        gameController.setFields(initialMoveResponse);

        $http.post("/games/").then(function (response) {
            const startGameResponse = response.data;
            $scope.gameId = startGameResponse.id;
            $scope.gameUrl = startGameResponse.uri;
        });


        gameController.move = function (pit) {
            const pitId = pit.id;
            console.log("PIT ID " + pitId);

            $scope.errorMessage = "";

            $http.put("/games/" + $scope.gameId + "/pits/" + pitId + "").then(function (response) {
                gameController.setFields(response.data);

                determineActivePlayer(pit);
                determineWinner(response.data)

            }).catch(function (response) {
                console.log(response);
                $scope.errorMessage = response.data.message;
            });

        };

        const determineWinner = function () {
            const collectedStone = $scope.houseAStone + $scope.houseBStone;
            const totalStone = TOTAL_STONE_COUNT;
            if (collectedStone == totalStone) {
                if ($scope.houseAStone > $scope.houseBStone) {
                    $scope.winner = "Player A";
                } else if ($scope.houseBStone > $scope.houseAStone) {
                    $scope.winner = "Player B";
                } else {
                    $scope.winner = "Game ended in a draw!";
                }
                $scope.gameStatus = "FINISH";
            }
        };

        const determineActivePlayer = function (pit) {
            if ($scope.activePlayer && isLastPitRule(pit)) {
                $scope.activePlayer = $scope.activePlayer == "Player A" ? "Player B" : "Player A"
            } else {
                $scope.activePlayer = pit.id < PIT_MEDIAN_ID ? "Player A" : "Player B"
            }
        };


        const isLastPitRule = function (pit) {
            if (pit.id < PIT_MEDIAN_ID) {
                return pit.stone = pit.id + HOUSE_A_PIT_ID;
            } else if (pit.id !== HOUSE_A_PIT_ID, pit.id !== HOUSE_B_PIT_ID) {
                return pit.stone = pit.id + HOUSE_B_PIT_ID;
            }
        };

    });

const initStatus = function () {
    const status = [];
    for (let i = PIT_START_ID; i <= PIT_END_ID; i++) {
        const item = {i, DEFAULT_PIT_COUNT};
        status.push(item)
    }
    return status;
};

const initialMoveResponse = {
    id: "",
    url: "",
    // status: {"1": "6", "2": "6", "3": "6", "4": "6", "5": "6", "6": "6", "7": "0", "8": "6", "9": "6", "10": "6", "11": "6", "12": "6", "13": "6", "14": "0"}
    status: {"1": "4", "2": "4", "3": "4", "4": "4", "5": "4", "6": "4", "7": "0", "8": "4", "9": "4", "10": "4", "11": "4", "12": "4", "13": "4", "14": "0"}
};
