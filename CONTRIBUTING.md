# Contributing to Konna

## ⚠️ Warning! ⚠️

* All code that is about to be merged must be written according to the codestyle
(see [CODESTYLE.md](CODESTYLE.md) for more information). Same for commit messages.
* All features should fit project core principles and roadmap.
* In case if the feature is not in the roadmap, then it should be discussed.

## No AI is allowed!

* PRs written, even partially, with AI will be rejected if its author will not intend
  to remove AI-generated code. Else the PR may remain until editing is completed.
* AI-generated documentation is discouraged
* AI-assisted code review is discouraged, _but_ it may be used if it provides actually useful advice. In that case better
  perform the review locally and then attach its results to a PR
* _You can use AI to ask questions during development, but there must be not AI-generated code in the project_ Game engine
  is not a place where you can ball around and sacrifice stability for development speed.
* All PRs, containing `AGENTS.md` will be rejected without investigation

## Community standards

* Standard files of the repo are [readme](README.md), [contributing guide](CONTRIBUTING.md) and [codestyle guide](CODESTYLE.md)
* If you think that standard files of the repo need to be rewritten or clarified,
then the help will be highly appreciated
* It is important to notice that such changes must be explained in a PR comments as the project owner
does not have enough experience in maintaining public projects

## Pull requests

* Pull request title should match: ```[#<issue>] <keyword>: <PR summary>```. It is allowed to address multiple
  issues, so then need to be separated with a comma.
* Pull request title example: ```[#4] Feat: add somethhing feature```
* Keyword is taken from the [codestyle guide](CODESTYLE.md) and should be started with a capital letter.
* Pull request description should describe all the work you have done as it will be included in the changelog.
  That means you need to include sec, feat, fix and ref commits to the description. However, 
  other commit types are highly appreciated to be in the description.
  Also, don't include issue number in the PR description because it is already in the title.
* If a PR fails checks, it won't be merged until all issues are fixed.
* A PR have to be reviewed and approved by the project owner himself
* After merging, the branch from that changes were taken should be deleted.
* If there is a comment in a review that asks about how it works, when answering make explanation simple yet full enough

## Feature requests

* You want to offer a feature that would be nice in Konna,
you need to contact the project owner first. Of course, you are not forced to do it,
but it may save a lot of time on the PR discussion
* The feature will be rejected if it doesn't meet the core principles (see above)

## Documentation

* All added code must be documented with Javadoc comments, with some exceptions
* Javadocs may not be written for:
  * zero-arg constructors
  * exception constructors, that are derived from `RuntimeException` class
  * private fields and methods
  * internal classes
  * test classes
* Always specify author and version (upcoming) for new classes. You don't need to specify version for methods that
  have been added in the same version as of their class.

## Testing

* All written code should be covered at least in the way overall project coverage
is equal or greater than 90%.
* All test classes have to extend in any way [KStandardTestClass](testing/src/main/java/io/github/darthakiranihil/konna/test/KStandardTestClass.java)
* Tests don't need to be documented
