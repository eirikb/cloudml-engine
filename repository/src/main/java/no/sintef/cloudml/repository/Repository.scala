package no.sintef.cloudml.repository

import no.sintef.cloudml.engine.Template

object Repository {

    def map(template :Template):  List[Instance] = {

        List(new Instance("group?", "dev"))
    }
}
