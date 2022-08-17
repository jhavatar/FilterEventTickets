package io.chthonic.events.data

import io.chthonic.events.domain.model.Event
import io.chthonic.events.data.model.EventType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventService @Inject constructor() {
    private var eventList: List<Event>? = null

    suspend fun getEvents(): List<Event> {
        return eventList ?: (withContext(Dispatchers.IO) {
            val parsed = parseEvents()
            getEvents(parsed)
        }).also { eventList = it }
    }

    /**
     * recursively retrieve nested events
     */
    private fun getEvents(eventType: EventType): List<Event> {
        val eventList = mutableListOf<Event>()
        eventList.addAll(eventType.events)
        eventType.children.forEach {
            eventList.addAll(getEvents(it))
        }
        return eventList
    }

    private fun parseEvents(): EventType =
        Json { ignoreUnknownKeys = true }.decodeFromString<EventType>(EVENTS_JSON.trimIndent())
}

private const val EVENTS_JSON = """
    {
      "id": 3,
      "name": "Concerts",
      "events": [],
      "children": [
        {
          "id": 1207,
          "name": "Rock and Pop",
          "events": [],
          "children": [
            {
              "id": 429,
              "name": "Backstreet Boys",
              "children": [],
              "events": [
                {
                  "id": 4422829,
                  "name": "Backstreet Boys",
                  "venueName": "Freedom Mortgage Pavilion",
                  "city": "Camden",
                  "price": 23,
                  "distanceFromVenue": 131162.390908814,
                  "date": "Jul 16 2020"
                },
                {
                  "id": 4422826,
                  "name": "Backstreet Boys",
                  "venueName": "Xfinity Theatre",
                  "city": "Hartford",
                  "price": 20,
                  "distanceFromVenue": 160356.993027791,
                  "date": "Jul 17 2022"
                },
                {
                  "id": 150029205,
                  "name": "Backstreet Boys",
                  "date": "Jul 24 2022",
                  "venueName": "Bethel Woods Center for the Arts",
                  "city": "Bethel",
                  "price": 69,
                  "distanceFromVenue": 122298.256317016
                }
              ]
            },
            {
              "name": "Justin Bieber",
              "id": 450948,
              "children": [],
              "events": [
                {
                  "id": 8621837,
                  "name": "Justin Bieber",
                  "date": "Jul 17 2022",
                  "venueName": "The OVO Hydro (formerly The SSE Hydro)",
                  "city": "Glasgow",
                  "price": 97,
                  "distanceFromVenue": 5181466.9468135
                },
                {
                  "id": 8621843,
                  "name": "Justin Bieber",
                  "date": "Jul 24 2022",
                  "venueName": "The O2",
                  "city": "London",
                  "price": 138,
                  "distanceFromVenue": 5581760.89658226
                },
                {
                  "id": 8621842,
                  "name": "Justin Bieber",
                  "date": "Jul 24 2022",
                  "venueName": "The O2",
                  "city": "London",
                  "price": 110,
                  "distanceFromVenue": 5581760.89658226
                }
              ]
            },
            {
              "name": "Elton John",
              "id": 43088,
              "children": [],
              "events": [
                {
                  "id": 6216095,
                  "name": "Elton John",
                  "date": "Jul 30 2022",
                  "venueName": "Progressive Field",
                  "city": "Cleveland",
                  "price": 133,
                  "distanceFromVenue": 643919.392660771
                },
                {
                  "id": 6222261,
                  "name": "Elton John",
                  "dayOfWeek": "Fri",
                  "date": "Aug 05 2022",
                  "venueName": "Soldier Field",
                  "city": "Chicago",
                  "price": 91,
                  "distanceFromVenue": 1138316.92169974
                },
                {
                  "id": 6217165,
                  "name": "Elton John",
                  "date": "Sep 07 2022",
                  "venueName": "Rogers Centre",
                  "city": "Chicago",
                  "price": 108,
                  "distanceFromVenue": 1138316.92169974
                }
              ]
            }
          ]
        },
        {
          "id": 1026,
          "name": "Rap/Hip-Hop/Reggae",
          "events": [],
          "children": [
            {
              "name": "Rage Against the Machine",
              "id": 100920451,
              "children": [],
              "events": [
                {
                  "id": 4423330,
                  "name": "Rage Against the Machine",
                  "date": "Aug 09 2022",
                  "venueName": "Madison Square Garden",
                  "city": "New York",
                  "price": 103,
                  "distanceFromVenue": 5571576.14652457
                },
                {
                  "id": 4430490,
                  "name": "Rage Against The Machine with Run The Jewels",
                  "url": "/rage-against-the-machine-new-york-tickets-8-11-2022/event/104589398/",
                  "date": "Aug 11 2022",
                  "venueName": "Madison Square Garden",
                  "city": "New York",
                  "price": 95,
                  "distanceFromVenue": 5571576.14652457
                },
                {
                  "id": 4922928,
                  "name": "Rage Against The Machine & Run The Jewels",
                  "date": "Aug 12 2022",
                  "venueName": "Madison Square Garden",
                  "city": "New York",
                  "price": 160,
                  "distanceFromVenue": 5571576.14652457
                }
              ]
            },
            {
              "name": "Daddy Yankee",
              "id": 66913,
              "children": [],
              "events": [
                {
                  "id": 10278327,
                  "name": "Daddy Yankee",
                  "date": "Aug 12 2022",
                  "venueName": "SAP Center",
                  "city": "San Jose",
                  "price": 130,
                  "distanceFromVenue": 8640632.67448427
                },
                {
                  "id": 10275468,
                  "name": "Daddy Yankee",
                  "date": "Aug 13 2022",
                  "venueName": "The Kia Forum",
                  "city": "Inglewood",
                  "price": 37,
                  "distanceFromVenue": 8776920.13120377
                },
                {
                  "id": 10380132,
                  "name": "Daddy Yankee",
                  "date": "Aug 15 2022",
                  "venueName": "The Kia Forum",
                  "city": "Inglewood",
                  "price": 16,
                  "distanceFromVenue": 8776920.13120377
                }
              ]
            }
          ]
        },
        {
          "id": 1027,
          "name": "R&B/Urban Soul",
          "events": [],
          "children": [
            {
              "name": "Chris Brown",
              "children": [],
              "id": 106817,
              "events": [
                {
                  "id": 150100779,
                  "name": "Chris Brown & Lil Baby",
                  "date": "Aug 12 2022",
                  "venueName": "iTHINK Financial Amphitheatre",
                  "city": "West Palm Beach",
                  "price": 19,
                  "distanceFromVenue": 7057650.78884398
                },
                {
                  "id": 150096789,
                  "name": "Chris Brown & Lil Baby",
                  "date": "Aug 13 2022",
                  "venueName": "MidFlorida Credit Union Amphitheatre at the Florida State Fairgrounds",
                  "city": "Tampa",
                  "price": 28,
                  "distanceFromVenue": 7099615.41662152
                }
              ]
            }
          ]
        }
      ]
    }
"""