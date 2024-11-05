package dat.routes;

import dat.controllers.TripController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoutes {

    private final TripController tripController = new TripController();

    public EndpointGroup getRoutes() {
        return () -> {
            post("/", tripController::create);
            get("/", tripController::readAll);
            get("/{id}", tripController::read);
            put("/{id}", tripController::update);
            delete("/{id}", tripController::delete);
            get("/{category}", tripController::readByCategory);
            post("/populate", tripController::populate);

            //put("/{tripId}/guides/{guideId}", tripController::updateGuideForTrip);
        };
    }
}
