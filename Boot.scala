package bootstrap.liftweb

import _root_.net.liftweb.util._
import _root_.net.liftweb.common._
import _root_.net.liftweb.http._
import _root_.net.liftweb.http.provider._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import _root_.net.liftweb.mapper.{DB, ConnectionManager, Schemifier, DefaultConnectionIdentifier, StandardDBVendor}
import _root_.java.sql.{Connection, DriverManager}
import _root_.your.proj.groupid.model._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    if (!DB.jndiJdbcConnAvailable_?) {
      val vendor = 
	new StandardDBVendor(Props.get("db.driver") openOr "org.h2.Driver",
			     Props.get("db.url") openOr 
			     "jdbc:h2:lift_proto.db;AUTO_SERVER=TRUE",
			     Props.get("db.user"), Props.get("db.password"))

      LiftRules.unloadHooks.append(vendor.closeAllConnections_! _)

      DB.defineConnectionManager(DefaultConnectionIdentifier, vendor)
    }

    // where to search snippet
    LiftRules.addToPackages("your.proj.groupid")
    Schemifier.schemify(true, Log.infoF _, User)

    // Build SiteMap
    val foo = Menu(Loc("Home", List("index"), "Home")) ::
    Menu(Loc("foo", List("css/index"), "foo")) ::
    Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
	     "Static Content")) ::
    Menu(Loc("foo1", List("foo1"), "foo1")) ::
    Menu(Loc("foo2", List("foo2"), "foo2")) ::
    Menu(Loc("foo3", List("foo3"), "foo3")) ::
    Menu(Loc("foo4", List("foo4"), "foo4")) ::
    Menu(Loc("foo5", List("foo5"), "foo5")) ::
    Menu(Loc("foo6", List("foo6"), "foo6")) ::
    Menu(Loc("foo7", List("foo7"), "foo7")) ::
    Menu(Loc("foo8", List("foo8"), "foo8")) ::
    Menu(Loc("foo9", List("foo9"), "foo9")) ::
    Menu(Loc("foo10", List("foo10"), "foo10")) ::
    Menu(Loc("foo11", List("foo11"), "foo11")) ::
    Menu(Loc("foo12", List("foo12"), "foo12")) ::
    Menu(Loc("foo13", List("foo13"), "foo13")) ::
    Menu(Loc("foo14", List("foo14"), "foo14")) ::
    Menu(Loc("foo15", List("foo15"), "foo15")) ::
    Menu(Loc("foo16", List("foo16"), "foo16")) ::
    Menu(Loc("foo17", List("foo17"), "foo17")) ::
    Menu(Loc("foo18", List("foo18"), "foo18")) ::
    Menu(Loc("foo19", List("foo19"), "foo19")) ::
    Menu(Loc("foo20", List("foo20"), "foo20")) ::
    Menu(Loc("foo21", List("foo21"), "foo21")) ::
    Menu(Loc("foo22", List("foo22"), "foo22")) ::
    Menu(Loc("foo23", List("foo23"), "foo23")) ::
    Menu(Loc("foo24", List("foo24"), "foo24")) ::
    Menu(Loc("foo25", List("foo25"), "foo25")) ::
    Menu(Loc("foo26", List("foo26"), "foo26")) ::
    Menu(Loc("foo27", List("foo27"), "foo27")) ::
    Menu(Loc("foo28", List("foo28"), "foo28")) ::
    Menu(Loc("foo29", List("foo29"), "foo29")) ::
    Menu(Loc("foo30", List("foo30"), "foo30")) ::
    Menu(Loc("foo31", List("foo31"), "foo31")) ::
    Menu(Loc("foo32", List("foo32"), "foo32")) ::
    Menu(Loc("foo33", List("foo33"), "foo33")) ::
    Menu(Loc("foo34", List("foo34"), "foo34")) ::
    Menu(Loc("foo35", List("foo35"), "foo35")) ::
    Menu(Loc("foo36", List("foo36"), "foo36")) ::
    Menu(Loc("foo37", List("foo37"), "foo37")) ::
    Menu(Loc("foo38", List("foo38"), "foo38")) ::
    Menu(Loc("foo39", List("foo39"), "foo39")) ::
    Menu(Loc("foo40", List("foo40"), "foo40")) ::
    Menu(Loc("foo41", List("foo41"), "foo41")) ::
    Menu(Loc("foo42", List("foo42"), "foo42")) ::
    Menu(Loc("foo43", List("foo43"), "foo43")) ::
    Menu(Loc("foo44", List("foo44"), "foo44")) ::
    Menu(Loc("foo45", List("foo45"), "foo45")) ::
    Menu(Loc("foo46", List("foo46"), "foo46")) ::
    Menu(Loc("foo47", List("foo47"), "foo47")) ::
    Menu(Loc("foo48", List("foo48"), "foo48")) ::
    Menu(Loc("foo49", List("foo49"), "foo49")) ::
    Menu(Loc("foo50", List("foo50"), "foo50")) ::
    User.sitemap

    val entries = User.sitemap

    LiftRules.setSiteMap(SiteMap(entries:_*))

    /*
     * Show the spinny image when an Ajax call starts
     */
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)

    /*
     * Make the spinny image go away when it ends
     */
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    LiftRules.early.append(makeUtf8)

    LiftRules.loggedInTest = Full(() => User.loggedIn_?)

    S.addAround(DB.buildLoanWrapper)
  }

  /**
   * Force the request to be UTF-8
   */
  private def makeUtf8(req: HTTPRequest) {
    req.setCharacterEncoding("UTF-8")
  }
}
