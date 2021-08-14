const DEFAULT_PIT_COUNT = 6;
const DEFAULT_STONE_COUNT = 6;
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
        $scope.activePlayer = "Start with one Player";
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
/*
            let  total = 0;

            for(let i= 1 ; i<= 14; i++ ){
                let value = $scope.pitMap[i];
                total += parseInt(value);
            }

            if(total !=72){
                $scope.BUG="BUG VAR !!!!!!";
                console.log("BUGG");
            }*/


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

            console.log("BeforSend: " + JSON.stringify($scope.pitMap))
            $http.put("/games/" + $scope.gameId + "/pits/" + pitId + "").then(function (response) {
                gameController.setFields(response.data);

                determineActivePlayer(pit);
                determineWinner(response.data)
                console.log("AfterSend: " + JSON.stringify($scope.pitMap))

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
                $scope.isGameOver = true;
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
    let status = {};
    for (let i = PIT_START_ID; i <= PIT_END_ID; i++) {
        status[i] = (i == HOUSE_A_PIT_ID | i == HOUSE_B_PIT_ID) ? 0 : DEFAULT_STONE_COUNT;
    }
    return status;
};

const initialMoveResponse = {
    id: "",
    url: "",
    status: initStatus()
};

